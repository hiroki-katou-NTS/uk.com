package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.Data;

@Data
public class LeaveHolidayState {

	
	private LeaveHolidayType leaveHolidayType;
	
	private String familyMemberId;
	
	private ReasonLeaveHoliday reasonLeaveHoliday;

	private MidweekClosure midweekClosure;
}
