/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Interface ScheduleBreakCalculationSetMemento.
 */
public interface ScheduleBreakCalculationSetMemento {

	/**
	 * Sets the checks if is refer rest time.
	 *
	 * @param val the new checks if is refer rest time
	 */
 	void setIsReferRestTime(boolean val);

	/**
	 * Sets the checks if is calc from schedule.
	 *
	 * @param val the new checks if is calc from schedule
	 */
 	void setIsCalcFromSchedule(boolean val);
}
