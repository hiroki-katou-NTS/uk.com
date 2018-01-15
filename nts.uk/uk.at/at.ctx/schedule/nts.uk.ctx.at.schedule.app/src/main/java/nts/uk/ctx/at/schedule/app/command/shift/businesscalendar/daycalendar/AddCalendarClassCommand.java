package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import lombok.Data;

@Data
public class AddCalendarClassCommand {

	private String companyId;
	
	private String classId;
	
	private String date;
	
	private int workingDayAtr;
}
