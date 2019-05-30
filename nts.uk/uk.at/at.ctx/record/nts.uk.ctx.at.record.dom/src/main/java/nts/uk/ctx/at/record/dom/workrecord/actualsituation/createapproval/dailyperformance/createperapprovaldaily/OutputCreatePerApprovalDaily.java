package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily;

import lombok.NoArgsConstructor;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
public class OutputCreatePerApprovalDaily {
	
	private boolean createperApprovalDaily ;
	
	private boolean checkStop = false;

	public OutputCreatePerApprovalDaily(boolean createperApprovalDaily, boolean checkStop) {
		super();
		this.createperApprovalDaily = createperApprovalDaily;
		this.checkStop = checkStop;
	}
	
}
