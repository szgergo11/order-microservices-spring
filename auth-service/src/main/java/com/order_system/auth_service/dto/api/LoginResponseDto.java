package com.order_system.auth_service.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LoginResponseDto {
    private String accessToken;
    private RefreshTokenResponseDto refreshToken;
}
