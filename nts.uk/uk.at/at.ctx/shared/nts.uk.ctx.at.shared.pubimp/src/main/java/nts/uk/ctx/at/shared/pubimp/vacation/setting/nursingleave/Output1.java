package nts.uk.ctx.at.shared.pubimp.vacation.setting.nursingleave;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class Output1 {
	
	private GeneralDate useStartDateBefore;
	
	private GeneralDate useEndDateBefore;
	
	private boolean periodGrantFlag;
	
	private GeneralDate useStartDateAfter;
	
	private GeneralDate useEndDateAfter;
	
	public Output1() {
		this.periodGrantFlag = false;
	}

}
