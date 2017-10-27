package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddCalendarClassCommand {

	private String companyId;
	
	private String classId;
	
	private BigDecimal dateId;
	
	private int workingDayAtr;
}
