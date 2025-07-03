package com.order_system.auth_service.service.auth;

import com.order_system.auth_service.entity.UserEntity;
import com.order_system.auth_service.dto.api.LoginRequestDto;
import com.order_system.auth_service.dto.api.LoginResponseDto;
import com.order_system.auth_service.dto.api.RefreshTokenResponseDto;
import com.order_system.auth_service.service.auth.token.AccessTokenService;
import com.order_system.auth_service.service.auth.token.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, AccessTokenService accessTokenService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.accessTokenService = accessTokenService;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        try {
            var authToken = new UsernamePasswordAuthenticationToken(
                    dto.getUsername(),
                    dto.getPassword()
            );
            Authentication auth = authenticationManager.authenticate(authToken);
            UserEntity user = (UserEntity) auth.getPrincipal();

            RefreshTokenResponseDto refreshToken = refreshTokenService.createRefreshToken(user.getId());
            String accessToken = accessTokenService.createAccessToken(user);

            return new LoginResponseDto(accessToken, refreshToken);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
