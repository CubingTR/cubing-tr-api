package org.cubingtr.cubingtrapi.wca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "wca", name = "competition_events", catalog = "wca")
@Getter
@Setter
public class WcaCompetitionEventEntity {

	@Id
	private Long id;

	private String competitionId;

	private String eventId;

	private String qualification;

}
