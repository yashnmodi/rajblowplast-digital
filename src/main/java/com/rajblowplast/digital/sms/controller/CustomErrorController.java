package com.rajblowplast.digital.sms.controller;

import com.rajblowplast.digital.sms.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomErrorController implements ErrorController {

    Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public ResponseEntity<Status> handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(null != status){
            int statusCode = Integer.parseInt(status.toString());
            logger.debug("Error thrown -- statusCode {}", statusCode);
            switch (statusCode){
                case 404:
                    Status s = new Status("2","404","Page not found.");
                    return new ResponseEntity<>(s, HttpStatus.BAD_REQUEST);
                case 500:
                    Status s2 = new Status("2","500","Internal Server Error.");
                    return new ResponseEntity<>(s2, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Status customStatus = new Status("2","E999", "Sorry! Something went wrong. Please try again.");
        return new ResponseEntity<>(customStatus, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Status> handleBadCredentialsException(Exception e){
        /* converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString(); */
        logger.error("Exception.getMessage--{}", e.getMessage());
        logger.error("Exception.getClass--{}", e.getClass());
        Status customStatus = new Status("2","E101", "Oops! You have entered invalid username/password.");
        return new ResponseEntity<>(customStatus, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Status> handleNullPointer(Exception e){
        logger.error("Exception.getMessage--{}", e.getMessage());
        Status customStatus = new Status("2","E998", "Sorry! Something went wrong. Please try again.");
        return new ResponseEntity<>(customStatus, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Status> handleNoHandlerFound(Exception e) {
        logger.error("Exception.getMessage--{}", e.getMessage());
        logger.error("Exception.getClass--{}", e.getClass());
        Status customStatus = new Status("2","E404", "Page not found.");
        return new ResponseEntity<>(customStatus, HttpStatus.NOT_FOUND);
    }

    //fallback method
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Status> handleFallback(Exception e){
        logger.error("Exception.getMessage--{}", e.getMessage());
        logger.error("Exception.getClass--{}", e.getClass());
        Status customStatus = new Status("2","E999", "Sorry! Something went wrong. Please try again.");
        return new ResponseEntity<>(customStatus, HttpStatus.BAD_REQUEST);
    }
}
