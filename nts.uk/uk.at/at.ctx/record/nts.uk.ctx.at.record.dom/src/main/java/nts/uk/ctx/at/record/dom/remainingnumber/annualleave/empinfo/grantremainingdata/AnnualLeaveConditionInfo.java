package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.base.YearDayNumber;

@Getter
public class AnnualLeaveConditionInfo {
	
	private YearDayNumber prescribedDays;
	
	private YearDayNumber deductedDays;
	
	private YearDayNumber workingDays;
	
}
