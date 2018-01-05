/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class LastWeekHolidayNumberOfOneWeek.
 */
// 1週間の最終週の公休日数
@Getter
@Setter
public class LastWeekHolidayNumberOfOneWeek extends DomainObject{
	
	/** The in legal holiday. */
	// 法定内休日日数
	private WeekNumberOfDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private WeekNumberOfDay outLegalHoliday;
	
	/**
	 * Instantiates a new last week holiday number of one week.
	 *
	 * @param inLegalHoliday the in legal holiday
	 * @param outLegalHoliday the out legal holiday
	 */
	public LastWeekHolidayNumberOfOneWeek(WeekNumberOfDay inLegalHoliday, WeekNumberOfDay outLegalHoliday) {
		this.inLegalHoliday = inLegalHoliday;
		this.outLegalHoliday = outLegalHoliday;
	}
}
