package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_CRITERION_MONEY_USAGE")
public class KagmtCriterionMoneyUsage extends ContractCompanyUkJpaEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "EMPLOYEMENT_USE")
    public int employmentUse;

	@Override
	protected Object getKey() {
		
		return this.companyId;
	}
	
	public static CriterionAmountUsageSetting toDomain(KagmtCriterionMoneyUsage entity) {
		return new CriterionAmountUsageSetting(entity.companyId,
				EnumAdaptor.valueOf(entity.employmentUse, NotUseAtr.class));
	}

	public static KagmtCriterionMoneyUsage toEntity(CriterionAmountUsageSetting domain) {
		return new KagmtCriterionMoneyUsage(domain.getCid(), domain.getEmploymentUse().value);
	}

}
