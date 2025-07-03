package com.order_system.auth_service.controller;

import com.order_system.auth_service.dto.api.LoginRequestDto;
import com.order_system.auth_service.dto.api.LoginResponseDto;
import com.order_system.auth_service.dto.api.RefreshAccessTokenResponseDto;
import com.order_system.auth_service.dto.api.RefreshTokenRequestDto;
import com.order_system.auth_service.service.auth.AuthService;
import com.order_system.auth_service.service.auth.token.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto){
        return authService.login(dto);
    }

    @PostMapping("/revoke")
    public void revokeRefreshToken(@RequestBody RefreshTokenRequestDto dto) {
        refreshTokenService.revokeRefreshToken(dto);
    }

    @PostMapping("/token")
    public RefreshAccessTokenResponseDto refreshAccessToken(@RequestBody RefreshTokenRequestDto dto) {
        return refreshTokenService.refreshAccessToken(dto);
    }
}