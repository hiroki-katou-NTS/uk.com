/**
 * 9:16:26 AM Jul 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * domain : 会社営業日カレンダー日次
 * @author tutk
 *
 */
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
