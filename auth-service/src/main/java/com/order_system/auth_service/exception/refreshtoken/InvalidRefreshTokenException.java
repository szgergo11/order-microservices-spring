package com.order_system.auth_service.exception.refreshtoken;

import org.springframework.security.core.AuthenticationException;

public class InvalidRefreshTokenException extends AuthenticationException {
    public InvalidRefreshTokenException(String msg) {
        super(msg);
    }
}