/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import nts.arc.time.GeneralDate;

/**
 * The Interface PublicHolidaySetMemento.
 */
public interface PublicHolidaySetMemento {
	
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	void setDate(GeneralDate date);

	/**
	 * Sets the day month.
	 *
	 * @param dayMonth the new day month
	 */
	void setDayMonth(int dayMonth);

	/**
	 * Sets the determine start date.
	 *
	 * @param determineStartDate the new determine start date
	 */
	void setDetermineStartDate(DayOfPublicHoliday determineStartDate);
}
