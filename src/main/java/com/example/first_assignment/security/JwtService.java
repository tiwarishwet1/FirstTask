package com.example.first_assignment.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    public static final String SECRET =
            "mySuperSecretKeyForEmployeeManagementSystem2026";

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String username, String role) {

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(
                        now.plus(1, ChronoUnit.HOURS)))
                .signWith(secretKey)
                .compact();
    }
}