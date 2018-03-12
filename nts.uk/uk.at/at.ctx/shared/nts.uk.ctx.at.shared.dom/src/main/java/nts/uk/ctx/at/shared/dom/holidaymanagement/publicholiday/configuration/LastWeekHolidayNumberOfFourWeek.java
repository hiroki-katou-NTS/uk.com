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
// ４週間の最終週の公休日数
public class LastWeekHolidayNumberOfFourWeek extends DomainObject{
	
	/** The in legal holiday. */
	// 法定内休日日数
	private FourWeekDay inLegalHoliday;
	
	/** The out legal holiday. */
	// 法定外休日日数
	private FourWeekDay outLegalHoliday;
	
	/**
	 * Instantiates a new last week holiday number of four week.
	 *
	 * @param inLegalHoliday the in legal holiday
	 * @param outLegalHoliday the out legal holiday
	 */
	public LastWeekHolidayNumberOfFourWeek(FourWeekDay inLegalHoliday, FourWeekDay outLegalHoliday) {
		this.inLegalHoliday = inLegalHoliday;
		this.outLegalHoliday = outLegalHoliday;
	}
}
