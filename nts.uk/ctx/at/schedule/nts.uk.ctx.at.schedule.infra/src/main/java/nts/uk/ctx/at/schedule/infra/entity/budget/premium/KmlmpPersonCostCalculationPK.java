package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KmlmpPersonCostCalculationPK {
	
	@Column(name="CID")
	public String CID;
	
	@Column(name="HISTORY_ID")
	public String HID;
}
