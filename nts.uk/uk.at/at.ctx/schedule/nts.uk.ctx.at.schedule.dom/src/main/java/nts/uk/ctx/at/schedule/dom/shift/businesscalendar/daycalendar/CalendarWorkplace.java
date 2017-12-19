package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
/**
 * domain : 職場営業日カレンダー日次
 * @author tutk
 *
 */
@Getter
public class CalendarWorkplace {
	private String workPlaceId;
	
	private GeneralDate date;
	
	private UseSet workingDayAtr;

	private CalendarWorkplace(String workPlaceId, GeneralDate date, UseSet workingDayAtr) {
		super();
		this.workPlaceId = workPlaceId;
		this.date = date;
		this.workingDayAtr = workingDayAtr;
	}

	public static CalendarWorkplace createFromJavaType(String workPlaceId, GeneralDate date, int workingDayAtr) {
		return new  CalendarWorkplace(workPlaceId, date, EnumAdaptor.valueOf(workingDayAtr, UseSet.class));
	}
	
	
	
	 
	
}
