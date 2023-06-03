package org.cubingtr.cubingtrapi.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cubingtr.cubingtrapi.account.entity.AccountEntity;
import org.cubingtr.cubingtrapi.account.service.AccountDomainService;
import org.cubingtr.cubingtrapi.auth.model.AuthenticatedUser;
import org.cubingtr.cubingtrapi.auth.service.JwtTokenUtil;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiSecurityContextRepository implements SecurityContextRepository {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String APPLICATION_HEADER = "Application";
	public static final String USER_LOGIN_REQUEST = "/auth/login";
	public static final String ACTUATOR_HEALTH_REQUEST = "/actuator/health";

	private List<String> whiteListUrls = Arrays.asList(USER_LOGIN_REQUEST);

	private final ObjectMapper objectMapper;

	private final JwtTokenUtil jwtTokenUtil;
	private final AccountDomainService accountDomainService;

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		String requestUri = requestResponseHolder.getRequest().getRequestURI();
		boolean isLoginRequest = USER_LOGIN_REQUEST.equals(requestUri);

		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		if (isLoginRequest) {
			return securityContext;
		}

		try {
			HttpServletRequest httpServletRequest = requestResponseHolder.getRequest();

			AuthenticatedUser authenticatedUser = fetchAuthenticatedUser(httpServletRequest);

			if (authenticatedUser == null) {
				securityContext.setAuthentication(null);
			} else {
				List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
				grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_API_USER"));
				if (authenticatedUser.getRolesSet() != null) {
					authenticatedUser.getRolesSet().forEach(roleName -> grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + roleName)));
				}

				Authentication authenticated = new UsernamePasswordAuthenticationToken(
						authenticatedUser,
						null,
						grantedAuthorityList
				);

				securityContext.setAuthentication(authenticated);
				MDC.put("username", authenticatedUser.getUsername());
			}
		} catch (Exception ex) {
			if (!whiteListUrls.contains(requestUri)) {
				log.debug("unauthorized request is received : {}", ex.getMessage());
			}
		}

		return securityContext;
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		String jwtToken = request.getHeader(AUTHORIZATION_HEADER);

		return jwtToken != null;
	}

	private AuthenticatedUser fetchAuthenticatedUser(HttpServletRequest request) {
		String jwtToken = request.getHeader(AUTHORIZATION_HEADER);

		if (jwtToken != null) {
			jwtToken = jwtToken.substring("Bearer ".length());
			return getAuthenticatedUserFromToken(jwtToken);
		}

		throw new UnauthorizedException("Token is missing");
	}

	private AuthenticatedUser getAuthenticatedUserFromToken(String securityJwtToken) {
		String username = jwtTokenUtil.getUsernameFromToken(securityJwtToken);
		String wcaId = jwtTokenUtil.getClaimFromToken(securityJwtToken, claims -> claims.get(JwtTokenUtil.WCA_ID, String.class));
		Long wcaPk = jwtTokenUtil.getClaimFromToken(securityJwtToken, claims -> claims.get(JwtTokenUtil.WCA_PK, Long.class));

		AccountEntity accountEntity = accountDomainService.findAccountByWcaPk(wcaPk);
		if (accountEntity == null) {
			return null;
		}

		AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
				.username(username)
				.wcaId(wcaId)
				.wcaPk(wcaPk)
				.build();

		return authenticatedUser;
	}

	@Override
	public void saveContext(SecurityContext arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
	}

}
