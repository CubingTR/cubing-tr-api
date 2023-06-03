package org.cubingtr.cubingtrapi.auth.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled =  true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final ApiSecurityContextRepository apiSecurityContextRepository;
	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final RestAccessDeniedHandler restAccessDeniedHandler;

	public static final String[] WHITE_LIST = {
			"/auth/login",
			"/actuator/health",
			"/actuator/info",
			"/v2/api-docs",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-resources/**",
			"/swagger-ui.html*",
			"/webjars/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.formLogin().disable()
		.httpBasic().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.securityContext().securityContextRepository(apiSecurityContextRepository)
				.and()
				.authorizeRequests()
				.antMatchers(WHITE_LIST).permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(new RestAuthenticationEntryPoint());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/error",
				"/actuator/health",
				"/v2/api-docs", "/webjars/**", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html*", // swagger
				"/*.html",
				"/favicon.ico",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js");
	}

	@Bean
	public ExceptionTranslationFilter exceptionTranslationFilter() {
		ExceptionTranslationFilter filter = new ExceptionTranslationFilter(restAuthenticationEntryPoint);
		return filter;
	}

}
