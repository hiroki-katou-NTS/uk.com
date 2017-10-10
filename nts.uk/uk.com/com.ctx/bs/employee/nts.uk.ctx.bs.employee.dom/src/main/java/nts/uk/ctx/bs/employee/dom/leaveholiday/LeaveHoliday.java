package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class LeaveHoliday extends AggregateRoot{

	private String sid;
	
	private String leaveHolidayId;
	
	private Period period;
	
	private LeaveHolidayState leaveHolidayState;
	
	
}
