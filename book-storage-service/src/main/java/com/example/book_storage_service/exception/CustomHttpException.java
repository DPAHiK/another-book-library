package com.example.book_storage_service.exception;

import org.springframework.http.HttpStatus;

public class CustomHttpException extends RuntimeException{

    private final HttpStatus errorCode;

    public CustomHttpException() {
        super();
        errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    
    public CustomHttpException(String msg, HttpStatus errorCode){
        super(msg);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode(){
        return errorCode;
    }

}