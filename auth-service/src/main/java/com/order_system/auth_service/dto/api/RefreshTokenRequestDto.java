package com.order_system.auth_service.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class RefreshTokenRequestDto {
    private UUID token;
}
