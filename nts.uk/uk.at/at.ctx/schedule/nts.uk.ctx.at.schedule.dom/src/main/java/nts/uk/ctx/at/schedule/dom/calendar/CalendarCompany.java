/**
 * 9:16:26 AM Jul 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.calendar;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
public class CalendarCompany {
	
	private String companyId;
	
	private BigDecimal dateId;
	
	private UseSet workingDayAtr;

	public CalendarCompany(String companyId, BigDecimal dateId, UseSet workingDayAtr) {
		super();
		this.companyId = companyId;
		this.dateId = dateId;
		this.workingDayAtr = workingDayAtr;
	}
	
	
	public static CalendarCompany createFromJavaType(String companyId, BigDecimal dateId, int workingDayAtr){
		return new CalendarCompany(companyId,dateId,EnumAdaptor.valueOf(workingDayAtr, UseSet.class));
	}
	
	
}
