package com.rajblowplast.digital.sms.controller;

import com.rajblowplast.digital.sms.model.Status;
import com.rajblowplast.digital.sms.service.EmailService;
import com.rajblowplast.digital.sms.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AlertsController {
    private static final Logger logger = LoggerFactory.getLogger(AlertsController.class);

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/sendEmail",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> sendEmail(HttpServletRequest request){
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        emailService.sendRegistrationOtpMail("User","myn.yashnm@gmail.com", "123456");
        response.put("status", new Status(AppConstants.SRC, AppConstants.SEC, AppConstants.SUCCESS));
        return new ResponseEntity<Map>(response, httpStatus);
    }
}
