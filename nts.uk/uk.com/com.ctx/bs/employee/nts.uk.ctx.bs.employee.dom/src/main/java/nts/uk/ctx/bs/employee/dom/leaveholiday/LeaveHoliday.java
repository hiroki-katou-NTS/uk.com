package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
public class LeaveHoliday extends AggregateRoot {

	/**
	 * domain: 休職休業
	 */
	/** 社員ID */
	private String sid;
	/** 休職休業ID */
	private String leaveHolidayId;
	/** 期間 */
	private DatePeriod period;
	/** LeaveHolidayState */
	private LeaveHolidayState leaveHolidayState;
	
}
