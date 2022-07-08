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
        List<DispatchDetails> data = new ArrayList<>();
        HttpStatus httpStatus = HttpStatus.OK;
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
                response.put("status", new Status(AppConstants.FRC, AppConstants.I204_MSG, AppConstants.I204_MSG));
            }
        } catch (Exception e) {
            logger.debug("Exception caught at getAllDispatched --- {}", e.getMessage());
            response.put("status", new Status(AppConstants.FRC, AppConstants.I203, AppConstants.I203_MSG));
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        //headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Map>(response, httpStatus);
    }

    @PostMapping(value = "/dispatched/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createDispatch(HttpServletRequest request, @RequestBody DispatchDetails body){
        logger.debug("uri = {}", request.getRequestURI());
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try{
            logger.debug("Request Body -- {}", body.toString());
            LocalDateTime current = LocalDateTime.now();
            body.setDispatchTime(current.format(AppConstants.dtf));
            dispatchRepo.save(body);
            response.put("status", new Status(AppConstants.SRC, AppConstants.SEC, AppConstants.I201_MSG));
            httpStatus = HttpStatus.CREATED;
        } catch (Exception e){
            logger.debug("Exception caught at createDispatch --- {}", e.getMessage());
            response.put("status", new Status(AppConstants.FRC, AppConstants.I203, AppConstants.I203_MSG));
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map>(response, httpStatus);
    }
}
