package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import lombok.Data;
//import nts.arc.time.GeneralDate;

@Data
public class UpdateCalendarClassCommand {

private String companyId;
	
	private String classId;
	
	private String date;
	
	private int workingDayAtr;
}
