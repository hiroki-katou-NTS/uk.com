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
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KmlspPremiumSetPK {
	
	@Column(name="CID")
	public String companyID;
	
	@Column(name="HIS_ID")
	public String historyID;
	
	@Column(name="PREMIUM_NO")
	public Integer displayNumber;
}
