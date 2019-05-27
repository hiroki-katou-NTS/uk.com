package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.createperapprovalmonthly;

import lombok.NoArgsConstructor;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
public class OutputCreatePerApprovalMon {
	
	private boolean createperApprovalMon ;
	
	private boolean checkStop = false;

	public OutputCreatePerApprovalMon(boolean createperApprovalMon, boolean checkStop) {
		super();
		this.createperApprovalMon = createperApprovalMon;
		this.checkStop = checkStop;
	}
	
}
