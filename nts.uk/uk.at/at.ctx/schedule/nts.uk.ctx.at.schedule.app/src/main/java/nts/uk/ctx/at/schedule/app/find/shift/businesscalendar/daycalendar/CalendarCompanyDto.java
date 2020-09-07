package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;


@Value
public class CalendarCompanyDto {
	
	private String companyId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	public static CalendarCompanyDto fromDomain(CalendarCompany domain){
		return new CalendarCompanyDto(
				domain.getCompanyId(),
				domain.getDate(),
				domain.getWorkDayDivision().value);
	}
}
