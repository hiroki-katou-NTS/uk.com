package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;

@Value
public class CalendarWorkplaceDto {
	
	private String WorkPlaceId;
	
	private GeneralDate date;
	
	private int workingDayAtr;
	
	public static CalendarWorkplaceDto fromDomain(CalendarWorkplace domain){
		return new CalendarWorkplaceDto(
				domain.getWorkPlaceId(),
				domain.getDate(),
				domain.getWorkDayDivision().value);
	} 
}
