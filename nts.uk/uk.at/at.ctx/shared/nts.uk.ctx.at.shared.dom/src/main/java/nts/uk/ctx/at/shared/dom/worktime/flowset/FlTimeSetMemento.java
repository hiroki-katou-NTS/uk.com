/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface FlowTimeSettingSetMemento.
 */
public interface FlTimeSetMemento {

	/**
	 * Sets the rouding.
	 *
	 * @param trSet the new rouding
	 */
	void setRouding(TimeRoundingSetting trSet);

	/**
	 * Sets the passage time.
	 *
	 * @param at the new passage time
	 */
	void setPassageTime(AttendanceTime at);
}
