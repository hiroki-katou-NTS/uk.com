package nts.uk.ctx.at.schedule.dom.calendar;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

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

	private CalendarWorkplace() {
		super();
	}

	public static CalendarWorkplace createFromJavaType(String workPlaceId, BigDecimal dateId, int workingDayAtr) {
		return new  CalendarWorkplace(workPlaceId, dateId, EnumAdaptor.valueOf(workingDayAtr, UseSet.class));
	}
	
	
	
	 
	
}
