package com.example.book_auth_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book_auth_service.models.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token);
}
