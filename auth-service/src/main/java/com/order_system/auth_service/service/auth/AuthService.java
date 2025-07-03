package com.order_system.auth_service.service.auth;

import com.order_system.auth_service.dto.api.LoginRequestDto;
import com.order_system.auth_service.dto.api.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto dto);
}
