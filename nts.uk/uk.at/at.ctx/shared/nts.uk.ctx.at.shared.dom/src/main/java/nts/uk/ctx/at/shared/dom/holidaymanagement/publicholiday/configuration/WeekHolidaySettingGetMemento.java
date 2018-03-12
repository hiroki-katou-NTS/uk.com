/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

/**
 * The Interface WeekHolidaySettingGetMemento.
 */
public interface WeekHolidaySettingGetMemento {
	
	/**
	 * Gets the cid.
	 *
	 * @return the cid
	 */
	String getCID();
	
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
	
	/**
	 * Gets the start day.
	 *
	 * @return the start day
	 */
	DayOfWeek getStartDay();
}
