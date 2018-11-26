package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

public interface HolidayRemainMergeAdapter {

	public HolidayRemainMerEx getRemainMer(String employeeId, YearMonthPeriod period);
}
