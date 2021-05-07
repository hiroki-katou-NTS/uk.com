package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KagmtCriterionMoneyUsagePk {
	
	@Column(name = "CID")
	public String companyId;
}
