package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
public class KrcmtBentoPK {
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "HIST_ID")
	public String histID;
	
	@Column(name = "MENU_FRAME")
	public Integer frameNo;
	
}
