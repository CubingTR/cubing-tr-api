package org.cubingtr.cubingtrapi.auth.security;

import org.springframework.security.core.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {
	public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException(String message, Exception cause) {
		super(message, cause);
	}
}
