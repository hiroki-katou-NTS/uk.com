package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;

@Value
public class CalendarClassDto {
	
	private String companyId;
	
	private String classId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	public static CalendarClassDto fromDomain(CalendarClass domain){
		return new CalendarClassDto(
				domain.getCompanyId(),
				domain.getClassId().v(),
				domain.getDate(),
				domain.getWorkDayDivision().value);
	}
}
