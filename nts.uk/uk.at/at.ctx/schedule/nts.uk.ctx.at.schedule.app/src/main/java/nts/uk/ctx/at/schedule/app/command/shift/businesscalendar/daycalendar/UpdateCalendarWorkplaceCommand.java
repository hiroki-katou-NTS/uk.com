package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import lombok.Data;

@Data
public class UpdateCalendarWorkplaceCommand {
	private String WorkPlaceId;
	
	private String date;
	
	private int workingDayAtr;
}
