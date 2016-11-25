package nts.uk.ctx.core.infra.data.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;
import nts.arc.layer.infra.data.entity.type.LocalDateTimeToDBConverter;
import nts.arc.layer.infra.data.entity.type.LocalDateToDBConverter;

@Entity
@Table(name="SAMPLE_COMPANY")
public class SmpmtCompany extends AggregateTableEntity {
	
	@Id
	@Column(name = "CODE")
	public String code;
	
	@Column(name = "NAME")
	public String name;
}

/*
 Rules:
 
 	Naming:
 		| DB                   | Entity             |
 		+----------------------+--------------------+
 		| PPRMT_PERSON_COMMUTE | PprmtPersonCommute |
 	
 	Type:
 		| DB                | Entity     |
 		+-------------------+------------+
 		| char              | String     |
 		| varchar           | String     |
 		| nvarchar          | String     |
 		| decimal (integer) | int        |
 		| decimal           | BigDecimal | 
 		
*/