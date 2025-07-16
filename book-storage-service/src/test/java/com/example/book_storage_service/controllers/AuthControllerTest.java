package com.example.book_storage_service.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.book_storage_service.dto.JwtAuthenticationResponse;
import com.example.book_storage_service.dto.LoginRequest;
import com.example.book_storage_service.services.AuthenticationService;

class AuthControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void signUp_ValidRequest_ReturnsJwtResponse() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setToken("jwt.token.here");

        when(authenticationService.signUp(any(LoginRequest.class))).thenReturn(jwtResponse);

        // Act & Assert
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk());

        verify(authenticationService, times(1)).signUp(any(LoginRequest.class));
    }

    @Test
    void signIn_ValidRequest_ReturnsJwtResponse() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setToken("jwt.token.here");

        when(authenticationService.signIn(any(LoginRequest.class))).thenReturn(jwtResponse);

        // Act & Assert
        mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk());

        verify(authenticationService, times(1)).signIn(any(LoginRequest.class));
    }
}