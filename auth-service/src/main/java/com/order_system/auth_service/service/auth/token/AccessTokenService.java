package com.order_system.auth_service.service.auth.token;

import com.order_system.auth_service.entity.UserEntity;
import com.order_system.auth_service.exception.refreshtoken.InvalidAccessTokenException;

public interface AccessTokenService {
    String createAccessToken(UserEntity userEntity);
    void validateAccessToken(String token) throws InvalidAccessTokenException;
}
