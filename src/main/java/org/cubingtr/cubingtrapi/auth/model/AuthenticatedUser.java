package org.cubingtr.cubingtrapi.auth.model;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthenticatedUser {

	private String username;
	private String email;
	private String wcaId;
	private Long wcaPk;

	private Set<String> rolesSet;
}
