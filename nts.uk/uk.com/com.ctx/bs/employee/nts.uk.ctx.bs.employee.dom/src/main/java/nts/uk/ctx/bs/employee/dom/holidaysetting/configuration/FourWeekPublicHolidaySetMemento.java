/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

/**
 * The Interface FourWeekPublicHolidaySetMemento.
 */
public interface FourWeekPublicHolidaySetMemento {
	
	/**
	 * Sets the last week added days.
	 *
	 * @param lastWeekAddedDays the new last week added days
	 */
	void  setLastWeekAddedDays(LastWeekHolidayNumberOfFourWeek lastWeekAddedDays);

	/**
	 * Sets the in legal holiday.
	 *
	 * @param inLegalHoliday the new in legal holiday
	 */
	void setInLegalHoliday(FourWeekDay inLegalHoliday);

	/**
	 * Sets the out legal holiday.
	 *
	 * @param outLegalHoliday the new out legal holiday
	 */
	void setOutLegalHoliday(FourWeekDay outLegalHoliday);
}
