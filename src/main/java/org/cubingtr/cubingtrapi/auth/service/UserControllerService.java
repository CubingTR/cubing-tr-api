package org.cubingtr.cubingtrapi.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cubingtr.cubingtrapi.account.entity.AccountEntity;
import org.cubingtr.cubingtrapi.account.service.AccountDomainService;
import org.cubingtr.cubingtrapi.auth.model.AccountDto;
import org.cubingtr.cubingtrapi.auth.model.AuthenticatedUser;
import org.cubingtr.cubingtrapi.auth.model.LoginResponse;
import org.cubingtr.cubingtrapi.auth.security.UnauthorizedException;
import org.cubingtr.cubingtrapi.common.model.GenericDataMap;
import org.cubingtr.cubingtrapi.common.service.DateTimeConfig;
import org.cubingtr.cubingtrapi.wca.model.WcaAuthenticationRequest;
import org.cubingtr.cubingtrapi.wca.model.WcaAuthenticationResponse;
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
public class UserControllerService {

	@Value("${remote.wca-service.grant_type}")
	private String wcaServiceGrantType;

	@Value("${remote.wca-service.client_id}")
	private String wcaServiceClientId;

	@Value("${remote.wca-service.client_secret}")
	private String wcaServiceClientSecret;

	@Value("${remote.wca-service.redirect_uri}")
	private String wcaServiceRedirectUri;


	private final JwtTokenUtil jwtTokenUtil;
	private final WcaApiClient wcaApiClient;
	private final AccountDomainService accountDomainService;

	public LoginResponse login(String wcaAuthCode) {

		WcaAuthenticationRequest wcaAuthenticationRequest = WcaAuthenticationRequest.builder()
				.grant_type(wcaServiceGrantType)
				.client_id(wcaServiceClientId)
				.client_secret(wcaServiceClientSecret)
				.code(wcaAuthCode)
				.redirect_uri(wcaServiceRedirectUri)
				.build();

		WcaAuthenticationResponse wcaAuthenticationResponse = wcaApiClient.authenticate(wcaAuthenticationRequest);

		GenericDataMap aboutMeResponse = wcaApiClient.aboutMe("Bearer " + wcaAuthenticationResponse.getAccess_token());
		GenericDataMap meDataMap = GenericDataMap.from(aboutMeResponse.getAsMap("me"));

		Long userWCA_PK = meDataMap.getAsLong("id");
		String userWCA_ID = meDataMap.getAsString("wca_id");
		String userEmail = meDataMap.getAsString("email");

		createOrUpdateAccount(meDataMap);

		// Generate JwtToken from authenticatedUser
		Map<String, Object> tokenClaims = new HashMap<>();
		tokenClaims.put(JwtTokenUtil.WCA_ID, userWCA_ID);
		tokenClaims.put(JwtTokenUtil.WCA_PK, userWCA_PK);

		String jwtToken = jwtTokenUtil.generateToken(tokenClaims, userEmail);

		LoginResponse loginResponse = LoginResponse.builder()
				.accessToken(jwtToken)
				.roles(Collections.emptySet())
				.applicationArgs(new LinkedHashMap<>())
				.build();

		log.info("login token : {}", jwtToken);
		return loginResponse;
	}

	public AccountDto me() {
		AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (authenticatedUser == null) {
			throw new UnauthorizedException("invalid session");
		}

		AccountEntity accountEntity = accountDomainService.findAccountByWcaPk(authenticatedUser.getWcaPk());
		AccountDto accountDto = AccountDto.builder()
				.id(accountEntity.getId())
				.name(accountEntity.getName())
				.email(accountEntity.getEmail())
				.wcaId(accountEntity.getWcaId())
				.wcaPk(accountEntity.getWcaPk())
				.url(accountEntity.getUrl())
				.countryId(accountEntity.getCountryId())
				.countryName(accountEntity.getCountryName())
				.countryContinentId(accountEntity.getCountryContinentId())
				.countryIso2(accountEntity.getCountryIso2())
				.avatarUrl(accountEntity.getAvatarUrl())
				.avatarThumbUrl(accountEntity.getAvatarThumbUrl())
				.build();

		return accountDto;
	}

	private void createOrUpdateAccount(GenericDataMap meDataMap) {
		AccountEntity accountEntity = accountDomainService.findAccountByWcaPk(meDataMap.getAsLong("id"));
		if (accountEntity == null) {
			accountEntity = new AccountEntity();
		}

		accountEntity.setEmail(meDataMap.getAsString("email"));
		accountEntity.setName(meDataMap.getAsString("name"));
		accountEntity.setAccountClass(meDataMap.getAsString("class"));
		accountEntity.setWcaId(meDataMap.getAsString("wca_id"));
		accountEntity.setWcaPk(meDataMap.getAsLong("id"));
		accountEntity.setCreatedAt(DateTimeConfig.convertDateTimeStrToLocalDateTime(meDataMap.getAsString("created_at")));
		accountEntity.setUpdatedAt(DateTimeConfig.convertDateTimeStrToLocalDateTime(meDataMap.getAsString("updated_at")));
		accountEntity.setGender(meDataMap.getAsString("gender"));
		accountEntity.setUrl(meDataMap.getAsString("url"));
		accountEntity.setDateOfBirth(DateTimeConfig.convertDateStrToLocalDate(meDataMap.getAsString("dob")));

		// country object
		GenericDataMap countryDataMap = GenericDataMap.from(meDataMap.getAsMap("country"));
		accountEntity.setCountryId(countryDataMap.getAsString("id"));
		accountEntity.setCountryName(countryDataMap.getAsString("name"));
		accountEntity.setCountryContinentId(countryDataMap.getAsString("continentId"));
		accountEntity.setCountryIso2(countryDataMap.getAsString("iso2"));

		// avatar object
		GenericDataMap avatarDataMap = GenericDataMap.from(meDataMap.getAsMap("avatar"));
		accountEntity.setAvatarUrl(avatarDataMap.getAsString("url"));
		accountEntity.setAvatarThumbUrl(avatarDataMap.getAsString("thumb_url"));

		accountDomainService.save(accountEntity);
	}

}
