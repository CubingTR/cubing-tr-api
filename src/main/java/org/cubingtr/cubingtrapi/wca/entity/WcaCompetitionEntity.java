package org.cubingtr.cubingtrapi.wca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "wca", name = "Competitions", catalog = "wca")
@Getter
@Setter
public class WcaCompetitionEntity {

	@Id
	private String id;

	private String name;

	private String cityname;
	private String venue;
	private String venueaddress;
	private String venuedetails;

	private String countryid;

	private Integer year;
	private Integer month;
	private Integer day;
	private Integer endmonth;
	private Integer endday;

	@Column(name = "start_date", insertable = false, updatable = false)
	private LocalDate startDate;

	@Column(name = "start_date", insertable = false, updatable = false)
	private LocalDate endDate;

	@Transient
	private List<WcaCompetitionEventEntity> competitionEventEntityList = new ArrayList<>();

	@Transient
	private WcaRegistrationEntity wcaRegistrationEntity;

	@Transient
	private boolean isUserRegistered;

}
