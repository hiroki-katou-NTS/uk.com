package nts.uk.ctx.at.function.dom.alarm.alarmdata;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ValueExtractAlarm {
	private String workplaceID;
	private String employeeID;
	private GeneralDate alarmValueDate;
	private String classification; 
	private String alarmItem;
	private String alarmValueMessage;
	private String comment;
}
