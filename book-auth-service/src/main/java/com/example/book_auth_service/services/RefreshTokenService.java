package com.example.book_auth_service.services;

import com.example.book_auth_service.models.RefreshToken;
import com.example.book_auth_service.models.User;
import com.example.book_auth_service.repo.RefreshTokenRepository;
import com.example.book_auth_service.dto.RotatedRefreshToken;
import com.example.book_auth_service.exception.InvalidRefreshTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;


@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final long refreshExpiration;

    public RefreshTokenService(RefreshTokenRepository repository, @Value("${jwt.refresh.expiration}") long refreshExpiration) {
        this.repository = repository;
        this.refreshExpiration = refreshExpiration;
    }

    public String createToken(User user) {
        String rawToken = generateToken();

        RefreshToken entity = new RefreshToken();
        entity.setUser(user);
        entity.setToken(hashToken(rawToken));
        entity.setExpiresAt(Instant.now().plusMillis(refreshExpiration));

        repository.save(entity);

        return rawToken;
    }

    public String generateToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    public String hashToken(String token) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @Transactional
    public RotatedRefreshToken rotateToken(String rawToken) {
        String tokenHash = hashToken(rawToken);

        RefreshToken oldToken = repository.findByToken(tokenHash).orElseThrow(InvalidRefreshTokenException::new);

        if (oldToken.getRevokedAt() != null || !oldToken.getExpiresAt().isAfter(Instant.now())) {
            throw new InvalidRefreshTokenException();
        }

        User user = oldToken.getUser();

        oldToken.setRevokedAt(Instant.now());
        repository.save(oldToken);

        String newRawToken = createToken(user);

        return new RotatedRefreshToken(user, newRawToken);
    }
}