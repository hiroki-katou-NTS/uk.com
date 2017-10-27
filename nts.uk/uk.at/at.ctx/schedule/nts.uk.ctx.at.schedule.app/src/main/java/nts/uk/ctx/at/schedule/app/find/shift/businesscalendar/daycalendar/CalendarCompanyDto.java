package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;


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
