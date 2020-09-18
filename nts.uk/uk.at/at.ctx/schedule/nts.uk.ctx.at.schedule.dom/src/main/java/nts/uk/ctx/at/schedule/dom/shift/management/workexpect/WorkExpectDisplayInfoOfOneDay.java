package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class WorkExpectDisplayInfoOfOneDay {
	
	private String employeeId;
	
	private GeneralDate expectingDate;
	
	private WorkExpectationMemo memo;
	
	private WorkExpectDisplayInfo displayInfo;

}
