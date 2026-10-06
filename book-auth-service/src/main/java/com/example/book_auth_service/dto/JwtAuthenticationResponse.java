package com.example.book_auth_service.dto;

public class JwtAuthenticationResponse {
    
    private String token;

    public JwtAuthenticationResponse(){}

    public JwtAuthenticationResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
