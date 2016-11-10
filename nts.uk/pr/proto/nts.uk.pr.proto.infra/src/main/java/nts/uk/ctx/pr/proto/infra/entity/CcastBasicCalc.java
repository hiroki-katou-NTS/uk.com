package nts.uk.ctx.pr.proto.infra.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name="CCAST_BASIC_CALC")
public class CcastBasicCalc extends AggregateTableEntity {

	@Id
	@Column(name = "CCD")
	public String ccd;
	
	@Column(name = "BASE_DAYS")
	public BigDecimal baseDays;
	
	@Column(name = "BASE_HOURS")
	public BigDecimal baseHours;
}
