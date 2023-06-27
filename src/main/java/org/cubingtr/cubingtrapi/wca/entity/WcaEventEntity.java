package org.cubingtr.cubingtrapi.wca.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "wca", name = "Events", catalog = "wca")
@Getter
@Setter
public class WcaEventEntity {

	@Id
	private String id;

	private String name;

	private Integer rank;

	private String format;

	private String cellname;

}
