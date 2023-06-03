package org.cubingtr.cubingtrapi.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
	@JsonProperty(value = "id")
	private Long id;

	@JsonProperty(value = "email")
	private String email;

	@JsonProperty(value = "wca_id")
	private String wcaId;

	@JsonProperty(value = "wca_pk")
	private Long wcaPk;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "account_class")
	private String accountClass;

	@JsonProperty(value = "date_of_birth")
	private LocalDate dateOfBirth;

	@JsonProperty(value = "country_id")
	private String countryId;

	@JsonProperty(value = "country_iso2")
	private String countryIso2;

	@JsonProperty(value = "country_name")
	private String countryName;

	@JsonProperty(value = "country_continent_id")
	private String countryContinentId;

	@JsonProperty(value = "city")
	private String city;

	@JsonProperty(value = "phone_no")
	private String phoneNo;

	@JsonProperty(value = "gender")
	private String gender;

	@JsonProperty(value = "created_at")
	private LocalDateTime createdAt;

	@JsonProperty(value = "updated_at")
	private LocalDateTime updatedAt;

	@JsonProperty(value = "url")
	private String url;

	@JsonProperty(value = "avatar_url")
	private String avatarUrl;

	@JsonProperty(value = "avatar_thumb_url")
	private String avatarThumbUrl;
}
