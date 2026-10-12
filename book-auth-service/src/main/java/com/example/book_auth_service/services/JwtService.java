package com.example.book_auth_service.services;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.book_auth_service.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
    @Value("${access.signing.key}")
    private String accessSigningKey;

    @Value("${refresh.signing.key}")
    private String refreshSigningKey;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public String generateAccessToken(User user) {
        return generateToken(
                user,
                accessSigningKey,
                accessExpiration,
                "access"
        );
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String generateToken(
            User user,
            String encodedSecret,
            long expiration,
            String tokenType
    ) {
        Date now = new Date();

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .claim("token_type", tokenType)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .signWith(getSigningKey(encodedSecret), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSigningKey(String encodedSecret) {
        byte[] keyBytes = Decoders.BASE64.decode(encodedSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(accessSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
