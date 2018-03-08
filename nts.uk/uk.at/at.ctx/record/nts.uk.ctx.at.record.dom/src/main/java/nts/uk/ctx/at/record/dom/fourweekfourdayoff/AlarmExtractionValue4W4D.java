package nts.uk.ctx.at.record.dom.fourweekfourdayoff;

import lombok.Value;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Value
public class AlarmExtractionValue4W4D {
	private String workplaceID;
	private String employeeID;
	private DatePeriod datePeriod;
	private int alarmCategory;
	private String classification; 
	private String alarmItem;
	private String alarmValueMessage;
	private String comment;
}
