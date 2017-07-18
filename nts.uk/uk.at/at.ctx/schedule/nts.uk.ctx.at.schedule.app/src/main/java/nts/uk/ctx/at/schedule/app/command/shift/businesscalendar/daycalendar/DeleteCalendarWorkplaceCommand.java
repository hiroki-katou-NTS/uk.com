package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import lombok.Data;
@Data
public class DeleteCalendarWorkplaceCommand {
	private String workPlaceId;
	
	private String yearMonth;
}
