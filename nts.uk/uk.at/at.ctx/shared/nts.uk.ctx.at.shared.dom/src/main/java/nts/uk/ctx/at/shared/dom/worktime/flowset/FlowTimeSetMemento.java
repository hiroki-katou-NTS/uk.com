/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface FlTimeSetMemento.
 */
public interface FlowTimeSetMemento {

	/**
	 * Sets the rouding.
	 *
	 * @param trSet the new rouding
	 */
	void setRouding(TimeRoundingSetting trSet);

	/**
	 * Sets the elapsed time.
	 *
	 * @param at the new elapsed time
	 */
	void setElapsedTime(AttendanceTime at);
}
