package com.example.book_storage_service.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.book_storage_service.exception.CustomHttpException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        String errorMessage = ex.getMessage();

        logger.error("Caught exception class: " + ex.getClass().getName() + 
                    "\nCaught exception message: " + errorMessage);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomHttpException.class)
    public ResponseEntity<String> handleCustomHttpException(CustomHttpException ex) {
        String errorMessage = ex.getMessage();

        logger.error("Caught exception class: " + ex.getClass().getName() + 
                    "\nHttp error code: " + ex.getErrorCode() +
                    "\nCaught exception message: " + errorMessage);

        return new ResponseEntity<>(errorMessage, ex.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        String errorMessage = ex.getMessage();

        logger.error("Caught exception class: " + ex.getClass().getName() + 
                    "\nCaught exception message: " + errorMessage);

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}