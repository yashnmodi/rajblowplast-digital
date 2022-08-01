package com.rajblowplast.digital.sms.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajblowplast.digital.sms.model.*;
import com.rajblowplast.digital.sms.service.EmailService;
import com.rajblowplast.digital.sms.service.JwtUserDetailsService;
import com.rajblowplast.digital.sms.util.AppConstants;
import com.rajblowplast.digital.sms.util.EncryptionUtil;
import com.rajblowplast.digital.sms.util.JwtTokenUtil;
import com.rajblowplast.digital.sms.service.OtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private OtpService otpService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request, @RequestBody LoginRequest loginRequest) throws Exception {
        String password = request.getHeader("x-pass");
        authenticate(loginRequest.getUsername(), password);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        LoginResponse loginResponse = new LoginResponse(userDetails.getUsername(), EncryptionUtil.encrypt(token));
        Status status = new Status(AppConstants.SRC, AppConstants.SEC, AppConstants.SUCCESS);
        loginResponse.setStatus(status);
        return ResponseEntity.ok(loginResponse);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            logger.error("---Disabled User---{}", e.getMessage());
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping(value = "/user/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createNewUser(HttpServletRequest request, @RequestBody String body) throws Exception {
        Status status = new Status();
        HttpStatus httpStatus = HttpStatus.OK;
        Map<String, Object> response = new HashMap<>();

        JsonNode jsonNode = new ObjectMapper().readTree(body);
        JsonNode authJson= jsonNode.has("auth")?jsonNode.get("auth"):null;
        JsonNode userJson = jsonNode.get("registration");
        AppUsers registrationObj = new ObjectMapper().treeToValue(userJson, AppUsers.class);

        // confirm call (2nd call) of /user/create API which verify OTP and register the user.
        if (null != authJson){
            Auth authObj =  new ObjectMapper().treeToValue(authJson, Auth.class);
            String otp = null;
            String email = null;
            String mobile = null;
            int otpId;
            if("verify".equals(authObj.getAuthAction())){
                otpId = authObj.getAuthId();
                if("OTP_EMAIL".equals(authObj.getAuthMode())){
                    otp = authObj.getMailOtp();
                    email = authObj.getAuthEmail();
                } else if ("OTP_MOBILE".equals(authObj.getAuthMobile())) {
                    otp = authObj.getMailOtp();
                    mobile = authObj.getAuthMobile();
                }

                // verification of OTP
                String result = otpService.verifyOtp(email, otpId, otp);

                if ("matched".equals(result)){
                    // on successful OTP verification create new user.
                    registrationObj = userDetailsService.addUser(registrationObj);
                    httpStatus = HttpStatus.CREATED;
                    response.put("status", new Status(AppConstants.SRC, AppConstants.SEC, AppConstants.SUCCESS));
                    return new ResponseEntity<>(response, httpStatus);
                } else if ("expired".equals(result)) {
                    response.put("status", new Status(AppConstants.FRC, AppConstants.FEC, "Your OTP is expired. Please try again."));
                } else if ("invalid".equals(result)) {
                    response.put("status", new Status(AppConstants.FRC, AppConstants.FEC, "Your OTP is incorrect. Please enter correct OTP."));
                }
            } else {
                throw new Exception();
            }
        } else {
            // initial call of /user/create API which will trigger OTP for authentication.
            if (!userDetailsService.existingUser(registrationObj.getEmail())) {
                registrationObj.setPassword(null);
                // generation and persistence of OTP
                Otp otpObj = otpService.generateOtp(registrationObj.getEmail());

                // sending OTP alert to user
                emailService.sendRegistrationOtpMail(registrationObj.getUsername(), registrationObj.getEmail(), otpObj.getOtp());

                // setting auth object
                Auth auth = new Auth();
                auth.setAuthMode("OTP_EMAIL");
                auth.setAuthAction("generated");
                auth.setAuthRedirection(request.getServletPath());
                auth.setAuthId(otpObj.getOtpId());
                auth.setAuthEmail(otpObj.getEmail());

                status.setReplyCode(AppConstants.ARC);
                status.setError(AppConstants.SEC);
                status.setReason(AppConstants.ARC_MSG);

                response.put("registration", registrationObj);
                response.put("auth", auth);
            } else {
                status.setReplyCode(AppConstants.FRC);
                status.setError(AppConstants.E402);
                status.setReason(AppConstants.E402_MSG);
            }
            response.put("status", status);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(value = "/verifyOtp",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> otpValidator(HttpServletRequest request, @RequestBody Otp body){
        java.util.Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        String email = body.getEmail();
        int otpId = body.getOtpId();
        String otp = body.getOtp();
        String status = otpService.verifyOtp(email, otpId, otp);

        return new ResponseEntity<Map>(response, httpStatus);
    }
}
