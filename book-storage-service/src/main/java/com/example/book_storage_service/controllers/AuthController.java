package com.example.book_storage_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.book_storage_service.dto.JwtAuthenticationResponse;
import com.example.book_storage_service.dto.LoginRequest;
import com.example.book_storage_service.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid LoginRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid LoginRequest request) {
        return authenticationService.signIn(request);
    }

}
