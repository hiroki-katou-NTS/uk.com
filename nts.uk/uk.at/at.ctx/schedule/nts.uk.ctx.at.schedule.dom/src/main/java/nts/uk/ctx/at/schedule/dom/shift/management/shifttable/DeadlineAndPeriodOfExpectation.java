package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Value
public class DeadlineAndPeriodOfExpectation {
	
	/**
	 * 締切日
	 */
	private GeneralDate deadline;
	
	/**
	 * 期間
	 */
	private DatePeriod period;
	
}
