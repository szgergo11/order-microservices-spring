package com.order_system.common.auth.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface JwtService {
    boolean isTokenValid(String token);

    Claims extractClaims(String token);

    String extractUsername(Claims claims);
    Integer extractUserId(Claims claims);
    Collection<? extends GrantedAuthority> extractAuthorities(Claims claims);
}
