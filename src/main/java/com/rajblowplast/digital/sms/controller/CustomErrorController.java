package com.rajblowplast.digital.sms.controller;

import com.rajblowplast.digital.sms.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public Map<String, Object> handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Map<String,Object> response = new HashMap<>();
        if(null != status){
            int statusCode = Integer.parseInt(status.toString());
            logger.debug("Error thrown -- statusCode {}", statusCode);
            switch (statusCode){
                case 404:
                    Status s = new Status("2","404","Page not found.");
                    response.put("status", s);
                    return response;
                case 500:
                    Status s2 = new Status("2","500","Internal Server Error.");
                    response.put("status", s2);
                    return response;
            }
        }
        response.put("status", new Status("2","99","Unknown Error."));
        return response;
    }
}
