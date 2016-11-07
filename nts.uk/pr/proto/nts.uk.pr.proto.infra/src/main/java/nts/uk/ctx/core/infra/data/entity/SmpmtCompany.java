package nts.uk.ctx.core.infra.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.AggregateTableEntity;

@Entity
@Table(name="SAMPLE_COMPANY")
@Getter
@Setter
public class SmpmtCompany extends AggregateTableEntity {
	
	@Id
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "NAME")
	private String name;
}
