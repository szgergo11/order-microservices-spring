package com.order_system.auth_service.service.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtGenerationService {
    String generateToken(UserDetails userDetails);
    String generateToken(UserDetails userDetails, Map<String, ?> extraClaims);
}
