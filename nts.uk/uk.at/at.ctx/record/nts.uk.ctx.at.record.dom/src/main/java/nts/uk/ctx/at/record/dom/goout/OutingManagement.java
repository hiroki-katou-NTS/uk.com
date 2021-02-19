package nts.uk.ctx.at.record.dom.goout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

@Getter
@NoArgsConstructor
public class OutingManagement extends AggregateRoot {
	
	private String companyId;
	
	private int maximumUsageCount;
	
	private GoingOutReason goingOutReason;

	public OutingManagement(String companyId, int maximumUsageCount, GoingOutReason goingOutReason) {
		super();
		this.companyId = companyId;
		this.maximumUsageCount = maximumUsageCount;
		this.goingOutReason = goingOutReason;
	}

}
