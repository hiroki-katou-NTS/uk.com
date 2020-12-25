package nts.uk.ctx.at.shared.infra.entity.scherec.dailyattdcal.premiumitem;

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
	public String companyID;
	
	@Column(name="HIS_ID")
	public String historyID;
}
