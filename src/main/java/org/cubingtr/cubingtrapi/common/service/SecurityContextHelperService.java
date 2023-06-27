package org.cubingtr.cubingtrapi.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cubingtr.cubingtrapi.account.entity.AccountEntity;
import org.cubingtr.cubingtrapi.account.service.AccountDomainService;
import org.cubingtr.cubingtrapi.auth.model.AccountDto;
import org.cubingtr.cubingtrapi.auth.model.AuthenticatedUser;
import org.cubingtr.cubingtrapi.auth.model.LoginResponse;
import org.cubingtr.cubingtrapi.auth.security.UnauthorizedException;
import org.cubingtr.cubingtrapi.auth.service.JwtTokenUtil;
import org.cubingtr.cubingtrapi.common.model.GenericDataMap;
import org.cubingtr.cubingtrapi.wca.entity.WcaCompetitionEntity;
import org.cubingtr.cubingtrapi.wca.model.WcaAuthenticationRequest;
import org.cubingtr.cubingtrapi.wca.model.WcaAuthenticationResponse;
import org.cubingtr.cubingtrapi.wca.repository.WcaCompetitionRepository;
import org.cubingtr.cubingtrapi.wca.service.WcaApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityContextHelperService {

	public AuthenticatedUser getAuthenticatedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof AuthenticatedUser) {
			return (AuthenticatedUser) principal;
		}

		return null;
	}

}
