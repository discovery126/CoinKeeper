package org.denis.coinkeeper.api.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
