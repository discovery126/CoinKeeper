package org.denis.coinkeeper.api.security;

import org.denis.coinkeeper.api.exceptions.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecuritySessionContext {

    public String getCurrentUserName() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.isAuthenticated()) {
            throw new UnauthorizedException("User not found in current session context");

        }
        return authentication.getName();
    }

}
