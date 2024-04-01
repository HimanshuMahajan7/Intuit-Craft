package com.intuit.comment.engine.auth;

import com.intuit.comment.engine.dto.response.UserAuthResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Copyright (C) @Himanshu - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * <p>
 * Author : Himanshu Mahajan
 * Created On : 20 Mar, 2024 (Wed)
 */

@Slf4j
@Service
public class JwtService {

    // TODO: Put this fields into application.properties file
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;
    private final String KEY = "4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
    private final SecretKey SECRET_KEY = getSecretKey();
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    @Autowired
    private ModelMapper mapper;

    /* retrieve username from jwt token */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UserAuthResponseDto extractUser(String token) {
        Map<String, String> user = extractClaim(token, claims -> claims.get("user", LinkedHashMap.class));
        return mapper.map(user, UserAuthResponseDto.class);
    }

    /* check if the token has expired */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /* retrieve expiration date from jwt token */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(UserAuthResponseDto user) {
        Map<String, Object> claimMap = Map.of("userId", user.getId(), "user", user);
        Claims claims = Jwts.claims().add(claimMap).build();
        return Jwts.builder()
                .subject(user.getEmail())
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean isValid(String token) {
        return !isTokenExpired(token);
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
