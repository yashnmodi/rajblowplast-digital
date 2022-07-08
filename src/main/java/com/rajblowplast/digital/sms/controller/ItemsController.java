package com.rajblowplast.digital.sms.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajblowplast.digital.sms.model.ItemDetails;
import com.rajblowplast.digital.sms.model.Status;
import com.rajblowplast.digital.sms.repository.ItemsRepo;
import com.rajblowplast.digital.sms.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ItemsController {
    Logger logger = LoggerFactory.getLogger(ItemsController.class);

    @Autowired
    ItemsRepo itemsRepo;

    @GetMapping(value="/items",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> fetchItems(HttpServletRequest request){
        logger.debug("uri = {}", request.getRequestURI());
        Map<String, Object> response = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.OK;
        List<ItemDetails> data = new ArrayList<>();
        try {
            data = itemsRepo.findAll();
            logger.debug("Total items={}", data.size());
            response.put("totalItems", data.size());
            response.put("Items", data);
            response.put("status", new Status(AppConstants.SRC, AppConstants.SEC, AppConstants.I201_MSG));
        } catch (Exception e){
            logger.debug("Exception caught at fetchItems --- {}", e.getMessage());
            response.put("status", new Status(AppConstants.FRC, AppConstants.I203, AppConstants.I203_MSG));
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map>(response, httpStatus);
    }

    @PostMapping(value="/items/create",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createItem(HttpServletRequest request, @RequestBody String body){
        logger.debug("uri = {}", request.getRequestURI());
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(body);
            logger.debug("Request Body -- {}", jsonNode.toString());
            String type = jsonNode.has("type")?jsonNode.get("type").textValue():null;
            String item = jsonNode.has("item")?jsonNode.get("item").textValue():null;
            boolean itemListed = itemsRepo.existsByName(item);
            if(!itemListed){
                long totalItems = itemsRepo.count();
                logger.debug("Total items={}", totalItems);
                StringBuilder skuID = new StringBuilder();
                String itemID = String.format("%04d" , totalItems+1);
                if("bottle".equalsIgnoreCase(type)){
                    skuID.append(AppConstants.SKU_CATEGORY_BOTTLES).append(itemID);
                } else if("cap".equalsIgnoreCase(type)){
                    skuID.append(AppConstants.SKU_CATEGORY_CAPS).append(itemID);
                }
                logger.debug("New skuID={}", skuID);
                if(null !=type && null != item) {
                    ItemDetails itemDetails = new ItemDetails(skuID.toString(), item);
                    itemsRepo.save(itemDetails);
                    response.put("totalItems", totalItems+1);
                    response.put("itemCreated", itemDetails);
                    httpStatus = HttpStatus.CREATED;
                    response.put("status", new Status(AppConstants.SRC, AppConstants.SEC, AppConstants.I201_MSG));
                } else {
                    response.put("status", new Status(AppConstants.FRC,AppConstants.I202, AppConstants.I202_MSG));
                }
            } else {
                response.put("status", new Status(AppConstants.FRC, AppConstants.I205, AppConstants.I205_MSG));
            }
        } catch (Exception e){
            logger.debug("Exception caught at createItem --- {}", e.getMessage());
            response.put("status", new Status(AppConstants.FRC, AppConstants.I203, AppConstants.I203_MSG));
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map>(response, httpStatus);
    }

    @PostMapping(value="/items/remove",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> removeItem(HttpServletRequest request, @RequestBody String body){
        logger.debug("uri = {}", request.getRequestURI());
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(body);
            logger.debug("Request Body -- {}", jsonNode.toString());
            String item = jsonNode.has("item")?jsonNode.get("item").textValue():null;
            if(null != item) {
                int deleted = itemsRepo.deleteByName(item);
                logger.debug("{} item(s) deleted successfully.",deleted);
                if(deleted==0){
                    response.put("status", new Status(AppConstants.FRC, AppConstants.I204, AppConstants.I204_MSG));
                } else {
                    response.put("deletedItems", deleted);
                    response.put("status", new Status(AppConstants.SRC, AppConstants.SEC, AppConstants.I201_MSG));
                }
            } else {
                response.put("status", new Status(AppConstants.FRC,AppConstants.I202, AppConstants.I202_MSG));
            }
        } catch (Exception e){
            logger.debug("Exception caught at createItem --- {}", e.getMessage());
            response.put("status", new Status(AppConstants.FRC,AppConstants.I203, AppConstants.I203_MSG));
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map>(response, httpStatus);
    }

}
