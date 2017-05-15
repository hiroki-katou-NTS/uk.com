package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KmlspExtraTimePK {
	@Column(name="CID")
	public String companyID;
	
	@Column(name="EXTRA_TIME_ID")
	public String extraTimeID;
}
