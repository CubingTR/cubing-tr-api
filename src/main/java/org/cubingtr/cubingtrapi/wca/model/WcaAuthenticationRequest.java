package org.cubingtr.cubingtrapi.wca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WcaAuthenticationRequest {

	private String grant_type;
	private String client_id;
	private String client_secret;
	private String code;
	private String redirect_uri;

}
