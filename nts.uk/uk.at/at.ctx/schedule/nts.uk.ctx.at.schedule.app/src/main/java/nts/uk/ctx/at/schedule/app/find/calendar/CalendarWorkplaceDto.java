package nts.uk.ctx.at.schedule.app.find.calendar;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkplace;

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
