package com.example.book_auth_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.example.book_auth_service.dto.JwtAuthenticationResponse;
import com.example.book_auth_service.dto.LoginRequest;
import com.example.book_auth_service.dto.RefreshTokenRequest;
import com.example.book_auth_service.services.AuthenticationService;
import com.example.book_auth_service.services.RefreshTokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid LoginRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid LoginRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/refresh")
    public JwtAuthenticationResponse refresh(@RequestBody RefreshTokenRequest request) {
        return authenticationService.refresh(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.revokeToken(request.getToken());
        return ResponseEntity.noContent().build();
    }

}
