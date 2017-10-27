package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UpdateCalendarCompanyCommand {
	private String companyId;
	
	private BigDecimal dateId;
	
	private int workingDayAtr;
}
