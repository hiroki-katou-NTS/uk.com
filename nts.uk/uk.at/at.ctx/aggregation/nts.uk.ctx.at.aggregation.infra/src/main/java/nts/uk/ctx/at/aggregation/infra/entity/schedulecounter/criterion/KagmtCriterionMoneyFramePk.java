package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KagmtCriterionMoneyFramePk {
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "FRAME_NO")
	public int frameNo;
}
