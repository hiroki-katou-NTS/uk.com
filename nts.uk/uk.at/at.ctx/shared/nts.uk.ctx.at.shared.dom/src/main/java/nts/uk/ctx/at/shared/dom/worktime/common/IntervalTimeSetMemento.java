/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface IntervalTimeSetMemento.
 */
public interface IntervalTimeSetMemento {

	/**
	 * Sets the interval time.
	 *
	 * @param intervalTime the new interval time
	 */
	void setIntervalTime(AttendanceTime intervalTime);
	
	
	/**
	 * Sets the rounding.
	 *
	 * @param rounding the new rounding
	 */
	void setRounding(TimeRoundingSetting rounding);
}
