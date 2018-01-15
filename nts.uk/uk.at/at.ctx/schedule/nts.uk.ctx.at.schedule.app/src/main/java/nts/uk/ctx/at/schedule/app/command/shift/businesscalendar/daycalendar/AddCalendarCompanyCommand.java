package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import lombok.Data;

@Data
public class AddCalendarCompanyCommand {

	private String companyId;
	
	private String date;
	
	private int workingDayAtr;
	
}
