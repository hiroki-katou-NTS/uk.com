package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * domain : 分類営業日カレンダー日次
 * @author tutk
 *
 */
@Getter
public class CalendarClass {
	
	private String companyId;
	
	private ClassID classId;
	
	private GeneralDate date;
	
	private WorkdayDivision WorkDayDivision;

	private CalendarClass(String companyId, ClassID classId, GeneralDate date, WorkdayDivision WorkDayDivision) {
		super();
		this.companyId = companyId;
		this.classId = classId;
		this.date = date;
		this.WorkDayDivision = WorkDayDivision;
	}

	public static CalendarClass createFromJavaType(String companyId, String classId, GeneralDate date, int WorkDayDivision) {
		
		return new CalendarClass(companyId, new ClassID(classId), date, EnumAdaptor.valueOf(WorkDayDivision, WorkdayDivision.class));
	}
	
	
	
	

	
}
