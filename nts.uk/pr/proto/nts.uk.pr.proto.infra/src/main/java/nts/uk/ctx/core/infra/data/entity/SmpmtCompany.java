package nts.uk.ctx.core.infra.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name="SAMPLE_COMPANY")
public class SmpmtCompany extends AggregateTableEntity {
	
	@Id
	@Column(name = "CODE")
	public String code;
	
	@Column(name = "NAME")
	public String name;
}
