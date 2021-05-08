package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author TU-TK
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KagmtCriterionAmountUsageSettingPK {
	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
}
