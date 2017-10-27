package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;

@Value
public class CalendarWorkplaceDto {
	
	private String WorkPlaceId;
	
	private BigDecimal dateId;
	
	private int workingDayAtr;
	
	public static CalendarWorkplaceDto fromDomain(CalendarWorkplace domain){
		return new CalendarWorkplaceDto(
				domain.getWorkPlaceId(),
				domain.getDateId(),
				domain.getWorkingDayAtr().value);
	} 
}
