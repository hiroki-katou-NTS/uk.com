/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaymanagement.configuration;

/**
 * The Interface FourWeekPublicHolidayGetMemento.
 */
public interface FourWeekPublicHolidayGetMemento {
	 
 	/**
 	 * Gets the last week added days.
 	 *
 	 * @return the last week added days
 	 */
 	LastWeekHolidayNumberOfFourWeek getLastWeekAddedDays();

	 /**
 	 * Gets the in legal holiday.
 	 *
 	 * @return the in legal holiday
 	 */
 	FourWeekDay getInLegalHoliday();

	 /**
 	 * Gets the out legal holiday.
 	 *
 	 * @return the out legal holiday
 	 */
 	FourWeekDay getOutLegalHoliday();
}
