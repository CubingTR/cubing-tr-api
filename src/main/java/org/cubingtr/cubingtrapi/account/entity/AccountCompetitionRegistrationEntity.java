package org.cubingtr.cubingtrapi.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "account_competition_registration",
		uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "competition_id", "event_id"}) }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCompetitionRegistrationEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "competition_id", nullable = false)
	private String competitionId;

	@Column(name = "event_id", nullable = false)
	private String eventId;

	@Column(name = "registration_date")
	private LocalDate registrationDate;

}
