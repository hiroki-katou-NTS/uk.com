package nts.uk.ctx.at.record.pub.workrecord.alarmlist.fourweekfourdayoff;

import lombok.Value;

@Value
public class AlarmExtractionValue4W4DExport {
	private String workplaceID;
	private String employeeID;
	private String datePeriod;
	private String classification; 
	private String alarmItem;
	private String alarmValueMessage;
	private String comment;
}
