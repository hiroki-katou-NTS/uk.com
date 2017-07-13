package nts.uk.ctx.at.schedule.app.command.calendar;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddCalendarCompanyCommand {

	private String companyId;
	
	private BigDecimal dateId;
	
	private int workingDayAtr;
	
}
