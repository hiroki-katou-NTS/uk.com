package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Value
public class ShiftTableRuleInfo {
	
	private GeneralDate deadline;
	
	private DatePeriod period;
	
}
