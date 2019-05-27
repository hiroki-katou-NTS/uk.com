package nts.uk.ctx.at.function.dom.adapter.workrecord.actualsituation.createperapprovaldaily;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutputCreatePerAppDailyImport {
	private boolean createperApprovalDaily ;
	
	private boolean checkStop = false;

	public OutputCreatePerAppDailyImport(boolean createperApprovalDaily, boolean checkStop) {
		super();
		this.createperApprovalDaily = createperApprovalDaily;
		this.checkStop = checkStop;
	}
	
}
