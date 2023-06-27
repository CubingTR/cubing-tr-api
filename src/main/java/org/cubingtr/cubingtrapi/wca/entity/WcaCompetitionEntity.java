package org.cubingtr.cubingtrapi.wca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

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

	private LocalDate startDate;
	private LocalDate endDate;
}
