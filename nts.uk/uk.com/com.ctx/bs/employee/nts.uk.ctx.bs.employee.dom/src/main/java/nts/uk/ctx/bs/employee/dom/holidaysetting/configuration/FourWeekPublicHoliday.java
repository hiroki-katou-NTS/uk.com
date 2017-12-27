/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
// 4週間の公休日数
public class FourWeekPublicHoliday extends DomainObject{
	
	/** The last week added days. */
	// 最終週の加算日数
	private LastWeekHolidayNumberOfFourWeek lastWeekAddedDays;
	
	/** The in legal holiday. */
	// 法定内休日日数
	private FourWeekDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private FourWeekDay outLegalHoliday;
	
	
	/**
	 * Instantiates a new four week public holiday.
	 *
	 * @param lastWeekAddedDays the last week added days
	 * @param inLegalHoliday the in legal holiday
	 * @param outLegalHoliday the out legal holiday
	 */
	public FourWeekPublicHoliday(LastWeekHolidayNumberOfFourWeek lastWeekAddedDays, FourWeekDay inLegalHoliday, FourWeekDay outLegalHoliday) {
		this.lastWeekAddedDays = lastWeekAddedDays;
		this.inLegalHoliday = inLegalHoliday;
		this.outLegalHoliday = outLegalHoliday;
	}
}
