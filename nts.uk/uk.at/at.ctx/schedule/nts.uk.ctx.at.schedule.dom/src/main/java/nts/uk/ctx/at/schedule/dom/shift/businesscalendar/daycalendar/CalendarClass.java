package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * domain : 分類営業日カレンダー日次
 * @author tutk
 *
 */
@Getter
public class CalendarClass {
	
	private String companyId;
	
	private ClassID classId;
	
	private BigDecimal dateId;
	
	private UseSet workingDayAtr;

	private CalendarClass(String companyId, ClassID classId, BigDecimal dateID, UseSet workingDayAtr) {
		super();
		this.companyId = companyId;
		this.classId = classId;
		this.dateId = dateID;
		this.workingDayAtr = workingDayAtr;
	}

	public static CalendarClass createFromJavaType(String companyId, String classId, BigDecimal dateId, int workingDayAtr) {
		
		return new CalendarClass(companyId,new ClassID(classId), dateId, EnumAdaptor.valueOf(workingDayAtr, UseSet.class));
	}
	
	
	
	

	
}
