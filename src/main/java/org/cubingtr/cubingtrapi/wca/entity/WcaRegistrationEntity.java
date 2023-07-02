package org.cubingtr.cubingtrapi.wca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "wca", name = "registrations", catalog = "wca")
@Getter
@Setter
public class WcaRegistrationEntity {

	@Id
	private Long id;

	@Column(name = "competition_id")
	private String competitionId;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "accepted_by")
	private Long acceptedBy;

	@Column(name = "guests")
	private Integer guests;

}
