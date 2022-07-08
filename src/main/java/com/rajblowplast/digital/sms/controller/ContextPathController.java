package com.rajblowplast.digital.sms.controller;

import com.rajblowplast.digital.sms.model.Status;
import com.rajblowplast.digital.sms.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ContextPathController {

    Logger logger = LoggerFactory.getLogger(ContextPathController.class);

    @GetMapping(value="/",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> contextRootMethod(){
        logger.debug("contextRoot method");
        Map<String,Object> response = new HashMap<>();
        response.put("status", new Status(AppConstants.SRC, AppConstants.SEC, AppConstants.SUCCESS));
        response.put("data", "Hello from Raj Blow Plast!");
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
