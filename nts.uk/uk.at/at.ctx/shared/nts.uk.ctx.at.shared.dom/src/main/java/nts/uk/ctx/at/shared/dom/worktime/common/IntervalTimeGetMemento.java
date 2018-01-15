/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface IntervalTimeGetMemento.
 */
public interface IntervalTimeGetMemento {
	
	/**
	 * Gets the interval time.
	 *
	 * @return the interval time
	 */
	AttendanceTime getIntervalTime();
	
	
	/**
	 * Gets the rounding.
	 *
	 * @return the rounding
	 */
	TimeRoundingSetting getRounding();

}
