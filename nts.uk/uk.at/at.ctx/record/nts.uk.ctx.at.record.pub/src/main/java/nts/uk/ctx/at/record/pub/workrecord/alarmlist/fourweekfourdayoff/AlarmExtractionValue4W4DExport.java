package nts.uk.ctx.at.record.pub.workrecord.alarmlist.fourweekfourdayoff;

import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;

@Value
public class AlarmExtractionValue4W4DExport {
	private String workplaceID;
	private String employeeID;
	private DatePeriod datePeriod;
	private String classification; 
	private String alarmItem;
	private String alarmValueMessage;
	private String comment;
	private String checkedValue;
}
