package com.example.book_auth_service.exception;

import org.springframework.http.HttpStatus;


public class InvalidRefreshTokenException extends CustomHttpException {

    public InvalidRefreshTokenException() {
        super("Invalid or expired refresh token", HttpStatus.UNAUTHORIZED);
    }
}