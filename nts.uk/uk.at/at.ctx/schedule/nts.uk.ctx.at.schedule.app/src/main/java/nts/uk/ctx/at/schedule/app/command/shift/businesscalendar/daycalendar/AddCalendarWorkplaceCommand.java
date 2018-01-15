package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import lombok.Data;

@Data
public class AddCalendarWorkplaceCommand {

	private String WorkPlaceId;
	
	private String date;
	
	private int workingDayAtr;
	
}
