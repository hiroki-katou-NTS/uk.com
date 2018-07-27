/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Interface FlowFixedRestSetSetMemento.
 */
public interface FlowFixedRestSetSetMemento {

	/**
	 * Sets the calculate method.
	 *
	 * @param val the new calculate method
	 */
	void setCalculateMethod(FlowFixedRestCalcMethod val);

	/**
	 * Sets the calculate from schedule.
	 *
	 * @param val the new calculate from schedule
	 */
	void setCalculateFromSchedule(ScheduleBreakCalculation val);

	/**
	 * Sets the calculate from stamp.
	 *
	 * @param val the new calculate from stamp
	 */
	void setCalculateFromStamp(StampBreakCalculation val);
}
