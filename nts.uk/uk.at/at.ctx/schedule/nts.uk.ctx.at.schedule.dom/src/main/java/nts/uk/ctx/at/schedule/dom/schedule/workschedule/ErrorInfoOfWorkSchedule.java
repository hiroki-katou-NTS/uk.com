package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ErrorInfoOfWorkSchedule {
	
	private String employeeId;
	
	private GeneralDate date;
	
	private String itemName;
	
	private String errorMessage;

}
