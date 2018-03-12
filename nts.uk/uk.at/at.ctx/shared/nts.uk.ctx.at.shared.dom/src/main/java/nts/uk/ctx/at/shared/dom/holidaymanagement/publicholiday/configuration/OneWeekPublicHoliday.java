/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
// 1週間の公休日数
public class OneWeekPublicHoliday extends DomainObject {
	
	/** The last week added days. */
	// 最終週の加算日数
	private LastWeekHolidayNumberOfOneWeek lastWeekAddedDays;
	
	/** The in legal holiday. */
	// 法定内休日日数
	private WeekNumberOfDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private WeekNumberOfDay outLegalHoliday;
	
	public OneWeekPublicHoliday(LastWeekHolidayNumberOfOneWeek lastWeekAddedDays, WeekNumberOfDay inLegalHoliday, WeekNumberOfDay outLegalHoliday) {
		this.lastWeekAddedDays = lastWeekAddedDays;
		this.inLegalHoliday = inLegalHoliday;
		this.outLegalHoliday = outLegalHoliday;
	}
}
