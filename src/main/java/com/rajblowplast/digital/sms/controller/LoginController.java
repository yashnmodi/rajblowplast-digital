package com.rajblowplast.digital.sms.controller;

import com.rajblowplast.digital.sms.model.AppUsers;
import com.rajblowplast.digital.sms.model.LoginRequest;
import com.rajblowplast.digital.sms.model.LoginResponse;
import com.rajblowplast.digital.sms.model.Status;
import com.rajblowplast.digital.sms.repository.UsersRepo;
import com.rajblowplast.digital.sms.service.JwtUserDetailsService;
import com.rajblowplast.digital.sms.util.AppConstants;
import com.rajblowplast.digital.sms.util.EncryptionUtil;
import com.rajblowplast.digital.sms.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
@CrossOrigin
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsersRepo usersRepo;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request, @RequestBody LoginRequest loginRequest) throws Exception {
        logger.debug("uri = {}", request.getRequestURI());
        String password = request.getHeader("x-pass");
        authenticate(loginRequest.getUsername(), password);
        final UserDetails userDetails = jwtUserDetailsService
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
    public ResponseEntity<?> createNewUser(HttpServletRequest request, @RequestBody AppUsers registration) throws Exception {
        logger.debug("uri = {}", request.getRequestURI());
        AppUsers userRegistration = new AppUsers();
        Status status = new Status();
        if(!usersRepo.existsByEmail(registration.getEmail())){
            userRegistration.setUsername(registration.getUsername());
            userRegistration.setEmail(registration.getEmail());
            userRegistration.setMobileNo(registration.getMobileNo());
            userRegistration.setLocked(false);
            userRegistration.setRole("admin");
            userRegistration.setPassword(new BCryptPasswordEncoder().encode(registration.getPassword()));
            LocalDateTime current = LocalDateTime.now();
            userRegistration.setRegistrationDate(current.format(AppConstants.dtf));
            userRegistration.setLastLoginDate("0");
            usersRepo.save(userRegistration);
            status.setReplyCode(AppConstants.SRC);
            status.setError(AppConstants.SEC);
            status.setReason(AppConstants.I201_MSG);
        } else {
           status.setReplyCode(AppConstants.FRC);
           status.setError(AppConstants.E402);
           status.setReason(AppConstants.E402_MSG);
        }
        return new ResponseEntity<Status>(status, HttpStatus.CREATED);
    }
}
