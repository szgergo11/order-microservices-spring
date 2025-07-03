package com.order_system.auth_service.service;

import com.order_system.auth_service.entity.RefreshTokenEntity;
import com.order_system.auth_service.entity.UserEntity;
import com.order_system.auth_service.dto.api.RefreshTokenRequestDto;
import com.order_system.auth_service.dto.api.RefreshTokenResponseDto;
import com.order_system.auth_service.dto.api.RefreshAccessTokenResponseDto;
import com.order_system.auth_service.exception.refreshtoken.InvalidRefreshTokenException;
import com.order_system.auth_service.repository.RefreshTokenRepository;
import com.order_system.auth_service.service.auth.token.AccessTokenService;
import com.order_system.auth_service.service.auth.token.RefreshTokenServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private AccessTokenService accessTokenService;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    @Spy
    private RefreshTokenServiceImpl refreshTokenService;


    private UserEntity testUser;
    private Integer testUserId;

    private RefreshTokenEntity testRefreshToken;
    private RefreshTokenRequestDto testRefreshTokenRequestDto;
    private UUID testRefreshTokenId;

    @BeforeEach
    void setUp() {
        testUserId = 1;
        testUser = new UserEntity();
        testUser.setId(testUserId);
        testUser.setUsername("user");
        testUser.setPassword("pw");

        testRefreshToken = new RefreshTokenEntity();
        testRefreshToken.setId(testRefreshTokenId);
        testRefreshToken.setUser(testUser);
        testRefreshToken.setCreatedAt(Instant.now());
        testRefreshToken.setExpiresAt(Instant.now().plus(Duration.ofDays(30)));
        testRefreshTokenRequestDto = new RefreshTokenRequestDto(testRefreshTokenId);

        when(entityManager.getReference(UserEntity.class, 1)).thenReturn(testUser);
    }

    @Test
    void createRefreshToken_ShouldCreateAndReturnRefreshTokenId() {
        // Arrange
        UUID newRefreshTokenId = UUID.randomUUID();
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenAnswer(invocation -> {
            RefreshTokenEntity entity = invocation.getArgument(0);
            entity.setId(newRefreshTokenId);
            return entity;
        });

        // Act
        RefreshTokenResponseDto refreshTokenResponseDto = refreshTokenService.createRefreshToken(testUser.getId());

        // Assert
        assertEquals(newRefreshTokenId, refreshTokenResponseDto.getToken());
    }

    @Test
    void refreshAccessToken_WithValidToken_ShouldReturnNewAccessToken() {
        // Arrange
        String expectedAccessToken = "access_token";
        UUID newRefreshTokenId = UUID.randomUUID();

        when(refreshTokenRepository.findByIdAndExpiresAtAfter(eq(testRefreshTokenId), any()))
                .thenReturn(Optional.of(testRefreshToken));
        when(accessTokenService.createAccessToken(testUser)).thenReturn(expectedAccessToken);
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenAnswer(invocation -> {
            RefreshTokenEntity entity = invocation.getArgument(0);
            entity.setId(newRefreshTokenId);
            return entity;
        });

        // Act
        RefreshAccessTokenResponseDto result = refreshTokenService.refreshAccessToken(testRefreshTokenRequestDto);

        // Assert
        assertEquals(expectedAccessToken, result.getAccessToken());
        verify(accessTokenService, times(1)).createAccessToken(any());
    }

    @Test
    void refreshAccessToken_WithValidToken_ShouldRotateRefreshToken() {
        // Arrange
        String expectedAccessToken = "access_token";
        UUID newRefreshTokenId = UUID.randomUUID();

        when(refreshTokenRepository.findByIdAndExpiresAtAfter(eq(testRefreshTokenId), any()))
                .thenReturn(Optional.of(testRefreshToken));
        when(accessTokenService.createAccessToken(testUser)).thenReturn(expectedAccessToken);
        when(refreshTokenRepository.save(any(RefreshTokenEntity.class))).thenAnswer(invocation -> {
            RefreshTokenEntity entity = invocation.getArgument(0);
            entity.setId(newRefreshTokenId);
            return entity;
        });

        // Act
        RefreshAccessTokenResponseDto result = refreshTokenService.refreshAccessToken(testRefreshTokenRequestDto);

        // Assert
        assertEquals(expectedAccessToken, result.getAccessToken());
        verify(accessTokenService, times(1)).createAccessToken(any());
        verify(refreshTokenService, times(1)).createRefreshToken(any());
        verify(refreshTokenService, times(1)).revokeRefreshToken(eq(testRefreshTokenRequestDto));
    }

    @Test
    void refreshAccessToken_WithInvalidToken_ShouldThrowException() {
        // Arrange
        when(refreshTokenRepository.findByIdAndExpiresAtAfter(eq(testRefreshTokenId), any()))
                .thenReturn(Optional.empty());

        // Act, Assert
        assertThrows(InvalidRefreshTokenException.class, () -> {
            refreshTokenService.refreshAccessToken(testRefreshTokenRequestDto);
        });
        verify(accessTokenService, never()).createAccessToken(any());
    }

    @Test
    void validateRefreshToken_WithValidToken_ShouldNotThrowException() {
        // Arrange
        when(refreshTokenRepository.existsByIdAndExpiresAtAfter(eq(testRefreshTokenId), any()))
                .thenReturn(true);

        // Act, Assert
        assertDoesNotThrow(() -> {
            refreshTokenService.validateRefreshToken(testRefreshTokenRequestDto);
        });
    }

    @Test
    void validateRefreshToken_WithInvalidToken_ShouldThrowException() {
        // Arrange
        when(refreshTokenRepository.existsByIdAndExpiresAtAfter(eq(testRefreshTokenId), any()))
                .thenReturn(false);

        // Act, Assert
        assertThrows(InvalidRefreshTokenException.class, () -> {
            refreshTokenService.validateRefreshToken(testRefreshTokenRequestDto);
        });
    }

    @Test
    void revokeRefreshToken_ShouldCallDelete() {
        // Arrange
        doNothing().when(refreshTokenRepository).deleteById(testRefreshTokenId);

        // Act
        refreshTokenService.revokeRefreshToken(testRefreshTokenRequestDto);

        // Assert
        verify(refreshTokenRepository, times(1)).deleteById(testRefreshTokenId);
    }
}
