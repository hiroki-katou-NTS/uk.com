package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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

	public static LeaveHoliday createLeaveHoliday(String sid, String leaveHolidayId, GeneralDate start, GeneralDate end,
			int leaveHolidayType, String familyMemberId, String reasonLeaveHoliday, boolean multiple,
			GeneralDate birthday) {
		DatePeriod period = new DatePeriod(start, end);
		LeaveHolidayState leaveHolidayState = LeaveHolidayState.createLeaveHolidayState(leaveHolidayType,
				familyMemberId, reasonLeaveHoliday, multiple, birthday);
		return new LeaveHoliday(sid, leaveHolidayId, period, leaveHolidayState);
	}

}
