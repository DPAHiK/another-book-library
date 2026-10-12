package com.example.book_auth_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.book_auth_service.dto.JwtAuthenticationResponse;
import com.example.book_auth_service.dto.LoginRequest;
import com.example.book_auth_service.dto.RefreshTokenRequest;
import com.example.book_auth_service.models.Role;
import com.example.book_auth_service.models.User;
import com.example.book_auth_service.dto.RotatedRefreshToken;

import io.jsonwebtoken.Claims;

@Service
public class AuthenticationService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    public JwtAuthenticationResponse signUp(LoginRequest request) {

        var user = new User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.addUser(user);


        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.createToken(user);
        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

    public JwtAuthenticationResponse signIn(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService.userByName(request.getUsername());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = refreshTokenService.createToken(user);
        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

public JwtAuthenticationResponse refresh(RefreshTokenRequest request) {
        String rawRefreshToken = request.getToken();
        RotatedRefreshToken rotated = refreshTokenService.rotateToken(rawRefreshToken);

        User user = rotated.user();
        String newAccessToken = jwtService.generateAccessToken(user);

        return new JwtAuthenticationResponse(
                newAccessToken,
                rotated.refreshToken()
        );
    }
}
