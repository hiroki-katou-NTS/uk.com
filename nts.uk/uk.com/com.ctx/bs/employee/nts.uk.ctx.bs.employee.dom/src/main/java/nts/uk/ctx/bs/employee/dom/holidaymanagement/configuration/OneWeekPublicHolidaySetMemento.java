/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaymanagement.configuration;

/**
 * The Interface OneWeekPublicHolidaySetMemento.
 */
public interface OneWeekPublicHolidaySetMemento {
	
	/**
	 * Sets the last week added days.
	 *
	 * @param lastWeekAddedDays the new last week added days
	 */
	void setLastWeekAddedDays(LastWeekHolidayNumberOfOneWeek lastWeekAddedDays);

	/**
	 * Sets the in legal holiday.
	 *
	 * @param inLegalHoliday the new in legal holiday
	 */
	void  setInLegalHoliday(WeekNumberOfDay inLegalHoliday);

	/**
	 * Sets the out legal holiday.
	 *
	 * @param outLegalHoliday the new out legal holiday
	 */
	void  setOutLegalHoliday(WeekNumberOfDay outLegalHoliday);
}
