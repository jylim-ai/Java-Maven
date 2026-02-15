package com.example.demo.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.Model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "b2c8f1e4a9d34f7e91c6a8d2f3e4c5b6b2c8f1e4a9d34f7e";
    private SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUuid().toString())
                .claim("role", user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }




    public String extractId(String token) {
        return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userid = extractId(token);
        return userid.equals(userDetails.getUsername());
    }
}

