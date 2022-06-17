package com.rajblowplast.digital.sms.controller;

import com.rajblowplast.digital.sms.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/")
    public ResponseEntity<?> contextRootMethod(){
        logger.debug("contextRoot method");
        Map<String,Object> response = new HashMap<>();
        response.put("status", new Status("0","0","Success"));
        Map<String,Object> data = new HashMap<>();
        data.put("msg1","Hello from Raj Blow Plast!");
        response.put("data", data);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Map>(response, headers, HttpStatus.OK);
    }
}
