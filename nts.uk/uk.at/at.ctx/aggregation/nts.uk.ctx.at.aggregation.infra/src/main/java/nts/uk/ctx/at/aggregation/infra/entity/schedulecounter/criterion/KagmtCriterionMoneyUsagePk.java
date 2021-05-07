package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KagmtCriterionMoneyUsagePk {
	
	@Column(name = "CID")
	public String companyId;
}
