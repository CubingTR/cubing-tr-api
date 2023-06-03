package org.cubingtr.cubingtrapi.common.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CryptoFeignException extends RuntimeException {
	private final String body;
	private final Integer status;

	@Override
	public String getMessage() {
		return String.format("Feign exception, status: %s, body: %s", status, body);
	}
}
