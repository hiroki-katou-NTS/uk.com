package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * domain : 職場営業日カレンダー日次
 * @author tutk
 *
 */
@Getter
public class CalendarWorkplace {
	private String workPlaceId;
	
	private GeneralDate date;
	
	private WorkdayDivision WorkDayDivision;

	private CalendarWorkplace(String workPlaceId, GeneralDate date, WorkdayDivision WorkDayDivision) {
		super();
		this.workPlaceId = workPlaceId;
		this.date = date;
		this.WorkDayDivision = WorkDayDivision;
	}

	public static CalendarWorkplace createFromJavaType(String workPlaceId, GeneralDate date, int WorkDayDivision) {
		return new  CalendarWorkplace(workPlaceId, date, EnumAdaptor.valueOf(WorkDayDivision, WorkdayDivision.class));
	}
	
	
	
	 
	
}
