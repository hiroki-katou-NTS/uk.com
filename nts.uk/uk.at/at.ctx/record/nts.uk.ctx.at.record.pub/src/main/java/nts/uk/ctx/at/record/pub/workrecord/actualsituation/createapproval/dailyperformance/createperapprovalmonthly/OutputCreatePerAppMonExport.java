package nts.uk.ctx.at.record.pub.workrecord.actualsituation.createapproval.dailyperformance.createperapprovalmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutputCreatePerAppMonExport {
	private boolean createperApprovalMon ;
	
	private boolean checkStop = false;

	public OutputCreatePerAppMonExport(boolean createperApprovalMon, boolean checkStop) {
		super();
		this.createperApprovalMon = createperApprovalMon;
		this.checkStop = checkStop;
	}
	
}
