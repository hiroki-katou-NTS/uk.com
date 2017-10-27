package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * domain : 職場営業日カレンダー日次
 * @author tutk
 *
 */
@Getter
public class CalendarWorkplace {
	private String workPlaceId;
	
	private BigDecimal dateId;
	
	private UseSet workingDayAtr;

	private CalendarWorkplace(String workPlaceId, BigDecimal dateId, UseSet workingDayAtr) {
		super();
		this.workPlaceId = workPlaceId;
		this.dateId = dateId;
		this.workingDayAtr = workingDayAtr;
	}

	public static CalendarWorkplace createFromJavaType(String workPlaceId, BigDecimal dateId, int workingDayAtr) {
		return new  CalendarWorkplace(workPlaceId, dateId, EnumAdaptor.valueOf(workingDayAtr, UseSet.class));
	}
	
	
	
	 
	
}
