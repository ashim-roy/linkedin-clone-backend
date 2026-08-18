package com.ashimCS.linkedinClone.userService.service;

import com.ashimCS.linkedinClone.userService.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    // Reads the JWT secret key from application.properties.
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    // Converts the secret string into a cryptographic SecretKey used for signing/verifying JWTs.
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(
                jwtSecretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateAccessToken(User user) {

        return Jwts.builder()
                // Stores user ID as the JWT subject.
                .subject(user.getId().toString())
                // Adds user's email as a custom claim.
                .claim("email", user.getEmail())
                // Stores the token creation time.
                .issuedAt(new Date())
                // Sets token expiration time (100 minutes from now).
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // valid for 1 hour
                // Digitally signs the token using our secret key.
                .signWith(getSecretKey())
                // Builds the JWT string.
                .compact();
    }

    public long getUserIdFromToken(String token) {
        // Parses and verifies the JWT using the same secret key.
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        // Gets the subject (user ID) from the token and converts it to Long.
        return Long.parseLong(claims.getSubject());
    }
}
