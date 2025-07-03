package com.order_system.auth_service.service.auth.token;

import com.order_system.auth_service.entity.RefreshTokenEntity;
import com.order_system.auth_service.entity.UserEntity;
import com.order_system.auth_service.dto.api.RefreshTokenRequestDto;
import com.order_system.auth_service.dto.api.RefreshTokenResponseDto;
import com.order_system.auth_service.dto.api.RefreshAccessTokenResponseDto;
import com.order_system.auth_service.exception.refreshtoken.InvalidRefreshTokenException;
import com.order_system.auth_service.repository.RefreshTokenRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenService accessTokenService;
    private final EntityManager entityManager;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, AccessTokenService accessTokenService, EntityManager entityManager) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenService = accessTokenService;
        this.entityManager = entityManager;
    }

    @Override
    public RefreshTokenResponseDto createRefreshToken(Integer userId) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(entityManager.getReference(UserEntity.class, userId));
        var now = Instant.now();
        refreshToken.setCreatedAt(now);
        refreshToken.setExpiresAt(now.plus(Duration.ofDays(7)));
        UUID refreshTokenId = refreshTokenRepository.save(refreshToken).getId();
        return new RefreshTokenResponseDto(refreshTokenId, refreshToken.getExpiresAt());
    }

    @Override
    public RefreshAccessTokenResponseDto refreshAccessToken(RefreshTokenRequestDto refreshToken) throws InvalidRefreshTokenException {
        var refreshTokenEntity = refreshTokenRepository.findByIdAndExpiresAtAfter(refreshToken.getToken(), Instant.now());
        if(refreshTokenEntity.isEmpty())
            throw new InvalidRefreshTokenException("Refresh token is invalid or expired");

        RefreshTokenResponseDto newRefreshToken = createRefreshToken(refreshTokenEntity.get().getUserId());
        revokeRefreshToken(refreshToken);

        String newAccessToken = accessTokenService.createAccessToken(refreshTokenEntity.get().getUser());
        return new RefreshAccessTokenResponseDto(newAccessToken, newRefreshToken);
    }

    @Override
    public void validateRefreshToken(RefreshTokenRequestDto refreshToken) {
        boolean exists = refreshTokenRepository.existsByIdAndExpiresAtAfter(refreshToken.getToken(), Instant.now());
        if(!exists)
            throw new InvalidRefreshTokenException("Refresh token is invalid or expired");
    }

    @Override
    public void revokeRefreshToken(RefreshTokenRequestDto refreshToken) {
        refreshTokenRepository.deleteById(refreshToken.getToken());
    }
}
