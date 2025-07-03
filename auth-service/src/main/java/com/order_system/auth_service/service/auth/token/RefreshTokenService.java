package com.order_system.auth_service.service.auth.token;

import com.order_system.auth_service.dto.api.RefreshTokenRequestDto;
import com.order_system.auth_service.dto.api.RefreshTokenResponseDto;
import com.order_system.auth_service.dto.api.RefreshAccessTokenResponseDto;
import com.order_system.auth_service.exception.refreshtoken.InvalidRefreshTokenException;

public interface RefreshTokenService {
    RefreshTokenResponseDto createRefreshToken(Integer userId);
    void validateRefreshToken(RefreshTokenRequestDto refreshToken) throws InvalidRefreshTokenException;
    void revokeRefreshToken(RefreshTokenRequestDto refreshToken);

    RefreshAccessTokenResponseDto refreshAccessToken(RefreshTokenRequestDto refreshToken) throws InvalidRefreshTokenException;
}
