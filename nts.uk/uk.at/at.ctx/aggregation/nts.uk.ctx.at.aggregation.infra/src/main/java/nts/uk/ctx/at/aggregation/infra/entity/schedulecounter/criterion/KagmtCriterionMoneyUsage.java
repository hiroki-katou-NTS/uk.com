package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import java.io.Serializable;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_CRITERION_MONEY_USAGE")
public class KagmtCriterionMoneyUsage extends ContractCompanyUkJpaEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KagmtCriterionMoneyUsagePk pk;
	
	@Column(name = "EMPLOYEMENT_USE")
    public int employmentUse;

	@Override
	protected Object getKey() {
		
		return this.pk;
	}

}
