package nts.uk.ctx.at.schedule.app.command.calendar;

import lombok.Data;

@Data
public class DeleteCalendarClassCommand {
	
	private String classId;
	
	private String yearMonth;
}
