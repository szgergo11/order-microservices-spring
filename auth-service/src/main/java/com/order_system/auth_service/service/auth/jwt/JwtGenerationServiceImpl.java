package com.order_system.auth_service.service.auth.jwt;

import com.order_system.common.auth.service.jwt.JwtKey;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGenerationServiceImpl implements JwtGenerationService{
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, Map.of());
    }

    @Override
    public String generateToken(UserDetails userDetails, Map<String, ?> extraClaims) {
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+150000))
                .signWith(JwtKey.getKey())
                .compact();
    }
}
