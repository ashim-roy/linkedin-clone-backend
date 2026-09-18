package com.ashim.linkedinClone.APIGateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

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

    // get principle or claims like EMail etc.
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
