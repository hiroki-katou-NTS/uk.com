/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Interface FlowRestSettingSetMemento.
 */
public interface FlowRestSettingSetMemento {

	/**
	 * Sets the flow rest time.
	 *
	 * @param time the new flow rest time
	 */
 	void setFlowRestTime(AttendanceTime time);

	/**
	 * Sets the flow passage time.
	 *
	 * @param time the new flow passage time
	 */
	 void setFlowPassageTime(AttendanceTime time);
}
