package nts.uk.ctx.at.schedule.pub.shift.management;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author tanlv
 *
 */
@Value
@AllArgsConstructor
public class BusinessDayCalendarExport {
	/** 休日区分 */
	public HolidayClsExport holidayCls;
	
	/** 対象日 */
	public GeneralDate targetDate;
}
