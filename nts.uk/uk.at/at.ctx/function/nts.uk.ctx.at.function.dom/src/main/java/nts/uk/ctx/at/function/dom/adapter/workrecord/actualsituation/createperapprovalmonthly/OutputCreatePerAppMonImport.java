package nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovalmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutputCreatePerAppMonImport {
	private boolean createperApprovalMon ;
	
	private boolean checkStop = false;

	public OutputCreatePerAppMonImport(boolean createperApprovalMon, boolean checkStop) {
		super();
		this.createperApprovalMon = createperApprovalMon;
		this.checkStop = checkStop;
	}
	
}
