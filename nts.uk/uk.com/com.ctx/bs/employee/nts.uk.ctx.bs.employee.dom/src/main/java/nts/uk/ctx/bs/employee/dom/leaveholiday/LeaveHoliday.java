package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

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
	private Period period;
	/** LeaveHolidayState */
	private LeaveHolidayState leaveHolidayState;
	
}
