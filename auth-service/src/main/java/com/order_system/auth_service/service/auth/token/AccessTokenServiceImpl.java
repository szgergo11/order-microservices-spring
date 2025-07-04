package com.order_system.auth_service.service.auth.token;

import com.order_system.auth_service.entity.UserEntity;
import com.order_system.auth_service.exception.refreshtoken.InvalidAccessTokenException;
import com.order_system.auth_service.service.auth.jwt.JwtGenerationService;
import com.order_system.common.auth.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {
    private final JwtGenerationService jwtGenerationService;
    private final JwtService jwtService;

    @Autowired
    public AccessTokenServiceImpl(JwtGenerationService jwtGenerationService, JwtService jwtService) {
        this.jwtGenerationService = jwtGenerationService;
        this.jwtService = jwtService;
    }

    @Override
    public String createAccessToken(UserEntity userEntity) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userid", userEntity.getId());

        return jwtGenerationService.generateToken(userEntity, extraClaims);
    }

    @Override
    public void validateAccessToken(String token) throws InvalidAccessTokenException {
        if(!jwtService.isTokenValid(token))
            throw new InvalidAccessTokenException("Access token is invalid or expired");
    }
}
