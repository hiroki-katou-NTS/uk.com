package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.budget.laborcost;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author TU-TK
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KagdtLaborCostBudgetPK {
	/**
	 * 対象組織の単位
	 */
	@Column(name = "TARGET_UNIT")
	public int targetUnit;

	/**
	 * 対象組織の単位に応じたID
	 */
	@Column(name = "TARGET_ID")
	public String targetId;

	/**
	 * 年月日
	 */
	@Column(name = "YMD")
	public GeneralDate ymd;
}
