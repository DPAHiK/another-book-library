package com.example.book_auth_service.dto;

import com.example.book_auth_service.models.User;

public record RotatedRefreshToken(
        User user,
        String refreshToken
) {}
