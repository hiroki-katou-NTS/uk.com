package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountUsageSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author TU-TK
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_CRITERION_MONEY_USAGE")
public class KagmtCriterionAmountUsageSetting extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KagmtCriterionAmountUsageSettingPK pk;

	/**
	 * 目安利用区分
	 */
	@Column(name = "EMPLOYEMENT_USE")
	public int employmentUse;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static KagmtCriterionAmountUsageSetting toEntity(CriterionAmountUsageSetting domain) {
		return new KagmtCriterionAmountUsageSetting(new KagmtCriterionAmountUsageSettingPK(domain.getCid()),
				domain.getEmploymentUse().value);
	}

	public CriterionAmountUsageSetting toDomain() {
		return new CriterionAmountUsageSetting(this.pk.companyId, NotUseAtr.valueOf(this.employmentUse));
	}
}
