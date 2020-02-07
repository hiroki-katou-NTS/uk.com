/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
public class DayMonthOutDto {

	/** The start date. */
	private GeneralDate startDate;
	
	/** The end date. */
	private GeneralDate endDate;
	
	/**
	 * Instantiates a new day month out dto.
	 *
	 * @param period the period
	 */
	public DayMonthOutDto(DatePeriod period) {
		this.startDate = period.start();
		this.endDate = period.end();
	}
}
