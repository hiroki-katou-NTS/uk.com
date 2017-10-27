package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddCalendarWorkplaceCommand {

	private String WorkPlaceId;
	
	private BigDecimal dateId;
	
	private int workingDayAtr;
	
}
