/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface FlowTimeSettingGetMemento.
 */
public interface FlTimeGetMemento {

	/**
	 * Gets the rouding.
	 *
	 * @return the rouding
	 */
	TimeRoundingSetting getRouding();

	/**
	 * Gets the passage time.
	 *
	 * @return the passage time
	 */
	AttendanceTime getPassageTime();
}
