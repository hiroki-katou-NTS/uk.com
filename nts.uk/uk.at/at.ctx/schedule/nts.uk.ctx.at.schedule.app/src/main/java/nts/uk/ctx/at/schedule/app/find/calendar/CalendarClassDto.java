package nts.uk.ctx.at.schedule.app.find.calendar;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarClass;

@Value
public class CalendarClassDto {
	
	private String companyId;
	
	private String classId;
	
	private BigDecimal dateId;
	
	private int workingDayAtr;
	
	public static CalendarClassDto fromDomain(CalendarClass domain){
		return new CalendarClassDto(
				domain.getCompanyId(),
				domain.getClassId().v(),
				domain.getDateId(),
				domain.getWorkingDayAtr().value);
	}
}
