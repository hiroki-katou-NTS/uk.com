package nts.uk.ctx.at.record.infra.entity.remainingnumber.resvlea;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtReverseLeaRemainPK {
	
	@Column(name = "SID")
	public String sid;

	@Column(name = "GRANT_DATE")
	public GeneralDate grantDate;

}
