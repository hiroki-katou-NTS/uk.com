/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaymanagement.configuration;

/**
 * The Interface OneWeekPublicHolidayGetMemento.
 */
public interface OneWeekPublicHolidayGetMemento {
	 
 	/**
 	 * Gets the last week added days.
 	 *
 	 * @return the last week added days
 	 */
 	LastWeekHolidayNumberOfOneWeek getLastWeekAddedDays();
	
	 /**
 	 * Gets the in legal holiday.
 	 *
 	 * @return the in legal holiday
 	 */
 	WeekNumberOfDay getInLegalHoliday();
	
	 /**
 	 * Gets the out legal holiday.
 	 *
 	 * @return the out legal holiday
 	 */
 	WeekNumberOfDay getOutLegalHoliday();
}
