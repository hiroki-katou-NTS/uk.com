/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface FlTimeGetMemento.
 */
public interface FlowTimeGetMemento {

	/**
	 * Gets the rouding.
	 *
	 * @return the rouding
	 */
	TimeRoundingSetting getRouding();

	/**
	 * Gets the elapsed time.
	 *
	 * @return the elapsed time
	 */
	AttendanceTime getElapsedTime();
}
