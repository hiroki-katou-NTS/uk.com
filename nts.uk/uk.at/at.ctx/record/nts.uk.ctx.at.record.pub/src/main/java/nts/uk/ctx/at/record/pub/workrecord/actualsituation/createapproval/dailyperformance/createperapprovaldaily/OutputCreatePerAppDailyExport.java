package nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovaldaily;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutputCreatePerAppDailyExport {
	private boolean createperApprovalDaily ;
	
	private boolean checkStop = false;

	public OutputCreatePerAppDailyExport(boolean createperApprovalDaily, boolean checkStop) {
		super();
		this.createperApprovalDaily = createperApprovalDaily;
		this.checkStop = checkStop;
	}
	
}
