package nts.uk.ctx.at.record.pub.remainnumber.holiday;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

public interface HdRemainDetailMerPub {
	/**
	 * Mer RQ265,268,269,363,364,369
	 * @param employeeId
	 * @param currentMonth
	 * @param baseDate
	 * @param period
	 * @return
	 */
	public HdRemainDetailMer getHdRemainDetailMer(String employeeId, YearMonth currentMonth, GeneralDate baseDate,
			DatePeriod period, CheckCallRQ checkCall);
}
