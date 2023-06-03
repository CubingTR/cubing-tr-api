package org.cubingtr.cubingtrapi.common.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String s, Response response) {
		String body;
		try (InputStream inputStream = response.body().asInputStream()) {
			body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			log.error("Error while reading error body", e);
			throw new RuntimeException("Error while reading error body", e);
		}

		log.error("Feign exception, status: {}, body: {}", response.status(), body);

		return CryptoFeignException.builder()
				.body(body)
				.status(response.status())
				.build();
	}

}
