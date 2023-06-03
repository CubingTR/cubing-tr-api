package org.cubingtr.cubingtrapi.common.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Logger;
import feign.codec.ErrorDecoder;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "org.cubingtr.cubingtrapi")
public class FeignConfig {

	@Value("${feign.okhttp.connection-timeout}")
	private Long connectionTimeout;
	@Value("${feign.okhttp.read-timeout}")
	private Long readTimeout;
	@Value("${feign.okhttp.write-timeout}")
	private Long writeTimeout;
	@Value("${feign.okhttp.retry}")
	private Boolean retry;

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient.Builder()
				.callTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
				.readTimeout(readTimeout, TimeUnit.MILLISECONDS)
				.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
				.retryOnConnectionFailure(retry)
				.connectionPool(new ConnectionPool(16, 5, TimeUnit.MINUTES))
				.build();
	}

	@Bean
	public ObjectMapper objectMapper() {
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class, new JacksonLocalDateTimeSerializer());
		return new ObjectMapper()
				.registerModule(javaTimeModule)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
				.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new FeignErrorDecoder();
	}

	@Bean
	public Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
