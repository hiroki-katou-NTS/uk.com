package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
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
	
	private UseSet workingDayAtr;

	private CalendarClass(String companyId, ClassID classId, GeneralDate date, UseSet workingDayAtr) {
		super();
		this.companyId = companyId;
		this.classId = classId;
		this.date = date;
		this.workingDayAtr = workingDayAtr;
	}

	public static CalendarClass createFromJavaType(String companyId, String classId, GeneralDate date, int workingDayAtr) {
		
		return new CalendarClass(companyId, new ClassID(classId), date, EnumAdaptor.valueOf(workingDayAtr, UseSet.class));
	}
	
	
	
	

	
}
