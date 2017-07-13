package nts.uk.ctx.at.schedule.app.find.calendar;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarCompany;


@Value
public class CalendarCompanyDto {
	
	private String companyId;
	
	private BigDecimal dateId;
	
	private int workingDayAtr;
	
	public static CalendarCompanyDto fromDomain(CalendarCompany domain){
		return new CalendarCompanyDto(
				domain.getCompanyId(),
				domain.getDateId(),
				domain.getWorkingDayAtr().value);
	}
}
