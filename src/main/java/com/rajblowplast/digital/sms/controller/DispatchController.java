package com.rajblowplast.digital.sms.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajblowplast.digital.sms.model.DispatchDetails;
import com.rajblowplast.digital.sms.model.ManufacturedDetails;
import com.rajblowplast.digital.sms.model.Status;
import com.rajblowplast.digital.sms.repository.DispatchRepo;
import com.rajblowplast.digital.sms.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DispatchController {
    Logger logger = LoggerFactory.getLogger(DispatchController.class);

    @Autowired
    DispatchRepo dispatchRepo;

    @PostMapping(value="/dispatched",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllDispatched(HttpServletRequest request, @RequestBody String body){
        logger.debug("uri = {}", request.getRequestURI());
        Map<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        List<DispatchDetails> data = new ArrayList<>();
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(body);
            logger.debug("Request Body -- {}", jsonNode.toString());
            String type = jsonNode.has("productType")?jsonNode.get("productType").textValue():"";
            if("Bottles".equalsIgnoreCase(type)) {
                data = dispatchRepo.findAllByProduct("Bottles");
                response.put("DispatchedItems", data);
            } else if ("Caps".equalsIgnoreCase(type)) {
                data = dispatchRepo.findAllByProduct("Caps");
                response.put("DispatchedItems", data);
            } else if ("All".equalsIgnoreCase(type)) {
                data = dispatchRepo.findAll();
                response.put("DispatchedItems", data);
            } else{
                response.put("status", new Status("2","001","No data found."));
            }
        } catch (Exception e) {
            logger.debug("Exception caught at getAllDispatched --- {}", e.getMessage());
        }
        //headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Map>(response, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/dispatched/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> removeItem(HttpServletRequest request, @RequestBody DispatchDetails body){
        logger.debug("uri = {}", request.getRequestURI());
        Map<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;
        try{
            logger.debug("Request Body -- {}", body.toString());
            LocalDateTime current = LocalDateTime.now();
            body.setDispatchTime(current.format(AppConstants.dtf));
            dispatchRepo.save(body);
            response.put("status", new Status("0","0","Success"));
            status = HttpStatus.CREATED;
        } catch (Exception e){
            logger.debug("Exception caught at removeItem --- {}", e.getMessage());
            response.put("status", new Status("2","002","Failure"));
            status = HttpStatus.NOT_ACCEPTABLE;
        }
        //headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Map>(response, headers, status);
    }
}
