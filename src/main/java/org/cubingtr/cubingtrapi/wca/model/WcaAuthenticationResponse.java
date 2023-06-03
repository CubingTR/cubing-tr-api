package org.cubingtr.cubingtrapi.wca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WcaAuthenticationResponse {

	private String access_token;
	private String token_type;
	private Integer expires_in;
	private String refresh_token;
	private String scope;
	private String created_at;

}
