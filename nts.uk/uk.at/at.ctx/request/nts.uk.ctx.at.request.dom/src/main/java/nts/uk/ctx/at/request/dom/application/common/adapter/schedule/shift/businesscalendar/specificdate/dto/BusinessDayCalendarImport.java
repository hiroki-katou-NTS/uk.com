package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@AllArgsConstructor
public class BusinessDayCalendarImport {
	/** 休日区分 */
	public HolidayClsImport holidayCls;
	
	/** 対象日 */
	public GeneralDate targetDate;
}
