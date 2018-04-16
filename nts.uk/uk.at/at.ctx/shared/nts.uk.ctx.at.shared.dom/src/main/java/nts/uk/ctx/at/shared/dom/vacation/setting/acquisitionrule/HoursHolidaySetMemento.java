/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

/**
 * The Interface HoursHolidaySetMemento.
 */
public interface HoursHolidaySetMemento {

	/**
	 * Sets the setting priorityPause
	 * @param priorityPause
	 */
	void SetPriorityOverpaid(boolean priorityOverpaid);
	
	/**
	 *  Sets the setting prioritySubstitute
	 * @param prioritySubstitute
	 */
	void SetSixtyHoursOverrideHoliday(boolean sixtyHoursOverrideHoliday);
}
