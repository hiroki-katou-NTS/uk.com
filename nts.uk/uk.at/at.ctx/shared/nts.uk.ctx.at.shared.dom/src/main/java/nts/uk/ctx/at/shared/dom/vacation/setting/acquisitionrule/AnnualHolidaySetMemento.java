/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule;

/**
 * The Interface AnnualHolidaySetMemento.
 */
public interface AnnualHolidaySetMemento {

	/**
	 * Sets the setting priorityPause
	 * @param priorityPause
	 */
	void SetPriorityPause(boolean priorityPause);
	
	/**
	 *  Sets the setting prioritySubstitute
	 * @param prioritySubstitute
	 */
	void SetPrioritySubstitute(boolean prioritySubstitute);
	
	/**
	 * Sets the setting sixtyHoursOverrideHoliday
	 * @param sixtyHoursOverrideHoliday
	 */
	void SetSixtyHoursOverrideHoliday(boolean sixtyHoursOverrideHoliday);
}
