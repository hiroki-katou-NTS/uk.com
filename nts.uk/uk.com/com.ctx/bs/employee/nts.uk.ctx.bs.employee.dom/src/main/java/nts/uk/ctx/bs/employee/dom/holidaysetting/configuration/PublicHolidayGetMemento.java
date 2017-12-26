/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

import nts.arc.time.GeneralDate;

/**
 * The Interface PublicHolidayGetMemento.
 */
public interface PublicHolidayGetMemento {
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	GeneralDate getDate();

	/**
	 * Gets the day month.
	 *
	 * @return the day month
	 */
	int getDayMonth();

	/**
	 * Gets the determine start date.
	 *
	 * @return the determine start date
	 */
	DayOfPublicHoliday getDetermineStartDate();
}
