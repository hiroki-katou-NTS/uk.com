package nts.uk.shr.infra.data.entity.company;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.AggregateTableEntity;

//@Entity
//@Table(name="SAMPLE_COMPANY")
@Getter
@Setter
public class SmpmtCompany extends AggregateTableEntity {
	
	//@Id
	//@Column(name = "CODE")
	private String code;
	
	//@Column(name = "NAME")
	private String name;
}
