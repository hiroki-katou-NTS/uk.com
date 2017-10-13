package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.Data;

@Data
public class LeaveHolidayState {

	/** 休業休職区分 */
	private LeaveHolidayType leaveHolidayType;
	
	/** 家族ID */
	private String familyMemberId;
	/**  理由 */
	private ReasonLeaveHoliday reasonLeaveHoliday;
	/** 産前休業 */
	private MidweekClosure midweekClosure;
}
