package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class KagmtCriterionMoneyFramePk {
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "FRAME_NO")
	public String frameNo;
}
