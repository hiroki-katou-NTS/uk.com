package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveHolidayState {

	/** 休業休職区分 */
	private LeaveHolidayType leaveHolidayType;

	/** 家族ID */
	private String familyMemberId;
	/** 理由 */
	private ReasonLeaveHoliday reasonLeaveHoliday;
	/** 産前休業 */
	private MidweekClosure midweekClosure;

	public static LeaveHolidayState createLeaveHolidayState(int leaveHolidayType, String familyMemberId,
			String reasonLeaveHoliday, boolean multiple, GeneralDate birthDate) {
		MidweekClosure midweekClosure = new MidweekClosure(multiple, birthDate);
		return new LeaveHolidayState(EnumAdaptor.valueOf(leaveHolidayType, LeaveHolidayType.class), familyMemberId,
				new ReasonLeaveHoliday(reasonLeaveHoliday), midweekClosure);
	}
}
