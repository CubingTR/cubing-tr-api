package org.cubingtr.cubingtrapi.account.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
	@SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
	@Column(name = "id", precision = 18)
	private Long id;

	@Column(name = "email", nullable = true, unique = true)
	private String email;

	@Column(name = "wca_id", nullable = true, unique = true)
	private String wcaId;

	@Column(name = "wca_pk", nullable = false, unique = true)
	private Long wcaPk;

	@Column(name = "name")
	private String name;

	@Column(name = "account_class")
	private String accountClass;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Column(name = "country_id")
	private String countryId;

	@Column(name = "country_iso2")
	private String countryIso2;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "country_continent_id")
	private String countryContinentId;

	@Column(name = "city")
	private String city;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "gender")
	private String gender;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdAt;

	@Column(name = "updated_at", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedAt;

	@Column(name = "url", length = 255)
	private String url;

	@Column(name = "avatar_url", length = 255)
	private String avatarUrl;

	@Column(name = "avatar_thumb_url", length = 255)
	private String avatarThumbUrl;

}
