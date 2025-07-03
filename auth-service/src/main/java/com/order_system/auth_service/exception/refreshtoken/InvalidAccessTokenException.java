package com.order_system.auth_service.exception.refreshtoken;

import org.springframework.security.core.AuthenticationException;

public class InvalidAccessTokenException extends AuthenticationException {
    public InvalidAccessTokenException(String msg) {
        super(msg);
    }
}