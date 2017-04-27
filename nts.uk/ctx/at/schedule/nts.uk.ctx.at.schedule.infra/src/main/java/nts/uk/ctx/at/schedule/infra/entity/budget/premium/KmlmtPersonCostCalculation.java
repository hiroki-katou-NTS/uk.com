package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KMLMT_PERSON_COST_CALC")
public class KmlmtPersonCostCalculation extends UkJpaEntity{
	@EmbeddedId
	public KmlmpPersonCostCalculationPK kmlmpPersonCostCalculationPK;
	
	@Column(name="MEMO")
	public String memo;
	
	@Column(name="UNIT_PRICE_ATR")
	public int unitPrice;
	
	@Column(name="START_DATE")
	public GeneralDate startDate;
	
	@Column(name="END_DATE")
	public GeneralDate endDate;

	@Override
	protected Object getKey() {
		return kmlmpPersonCostCalculationPK;
	}
}
