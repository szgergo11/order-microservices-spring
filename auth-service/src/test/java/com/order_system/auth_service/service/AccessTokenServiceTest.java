package com.order_system.auth_service.service;

import com.order_system.auth_service.entity.UserEntity;
import com.order_system.auth_service.exception.refreshtoken.InvalidAccessTokenException;
import com.order_system.auth_service.service.auth.jwt.JwtGenerationService;
import com.order_system.common.service.jwt.JwtService;
import com.order_system.auth_service.service.auth.token.AccessTokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccessTokenServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private JwtGenerationService jwtGenerationService;

    @InjectMocks
    private AccessTokenServiceImpl accessTokenService;

    @Test
    void createAccessToken_ShouldReturnTokenFromJwtService() {
        // Arrange
        UserEntity testUser = new UserEntity();
        testUser.setId(1);
        testUser.setUsername("user");
        testUser.setPassword("pw");

        String expectedToken = "token";
        when(jwtGenerationService.generateToken(any(UserEntity.class), anyMap())).thenReturn(expectedToken);

        // Act
        String result = accessTokenService.createAccessToken(testUser);

        // Assert
        assertEquals(expectedToken, result);
        verify(jwtGenerationService, times(1)).generateToken(any(), any());
    }

    @Test
    void validateAccessToken_WithValidToken_ShouldNotThrowException() {
        // Arrange
        String validToken = "token";
        when(jwtService.isTokenValid(validToken)).thenReturn(true);

        // Act, Assert
        assertDoesNotThrow(() -> accessTokenService.validateAccessToken(validToken));
    }

    @Test
    void validateAccessToken_WithInvalidToken_ShouldThrowInvalidAccessTokenException() {
        // Arrange
        String invalidToken = "token";
        when(jwtService.isTokenValid(invalidToken)).thenReturn(false);

        // Act, Assert
        assertThrows(
                InvalidAccessTokenException.class,
                () -> accessTokenService.validateAccessToken(invalidToken)
        );
    }
}
