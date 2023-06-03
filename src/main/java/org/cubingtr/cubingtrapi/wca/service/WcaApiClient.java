package org.cubingtr.cubingtrapi.wca.service;

import org.cubingtr.cubingtrapi.common.model.GenericDataMap;
import org.cubingtr.cubingtrapi.wca.model.WcaAuthenticationRequest;
import org.cubingtr.cubingtrapi.wca.model.WcaAuthenticationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.LinkedHashMap;
import java.util.Map;

@FeignClient(name = "wcaApiClient", url = "${remote.wca-service.url}")
public interface WcaApiClient {

	@PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	WcaAuthenticationResponse authenticate(@RequestBody WcaAuthenticationRequest wcaAuthenticationRequest);

	@GetMapping(value = "/api/v0/me")
	GenericDataMap aboutMe(@RequestHeader("Authorization") String bearerToken);

}
