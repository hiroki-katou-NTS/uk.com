/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.holidaysetting.configuration;

/**
 * The Interface LastWeekHolidayNumberOfOneWeekSetMemento.
 */
public interface LastWeekHolidayNumberOfOneWeekSetMemento {
	
	/**
	 * Sets the in legal holiday.
	 *
	 * @param inLegalHoliday the new in legal holiday
	 */
	void setInLegalHoliday(WeekNumberOfDay inLegalHoliday);
	
	/**
	 * Sets the out legal holiday.
	 *
	 * @param outLegalHoliday the new out legal holiday
	 */
	void setOutLegalHoliday(WeekNumberOfDay outLegalHoliday);
}
