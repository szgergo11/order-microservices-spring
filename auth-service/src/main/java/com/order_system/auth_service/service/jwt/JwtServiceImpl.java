package com.order_system.auth_service.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    @Override
    public Integer extractUserId(Claims claims) {
        return claims.get("userid", Integer.class);
    }

    @Override
    public boolean isTokenValid(String token) {
        try{
            Date d = extractClaim(token, Claims::getExpiration);
            return d.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(JwtKey.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Collection<? extends GrantedAuthority> extractAuthorities(Claims claims) {
        List<String> authorities = claims.get("authorities", List.class);
        if (authorities == null) {
            return Collections.emptyList();
        }
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractClaims(token));
    }
}
