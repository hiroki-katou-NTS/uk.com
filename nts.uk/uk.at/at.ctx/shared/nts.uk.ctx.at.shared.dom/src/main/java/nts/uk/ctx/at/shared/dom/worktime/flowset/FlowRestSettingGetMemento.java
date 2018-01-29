/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Interface FlowRestSettingGetMemento.
 */
public interface FlowRestSettingGetMemento {

	/**
	 * Gets the flow rest time.
	 *
	 * @return the flow rest time
	 */
 	AttendanceTime getFlowRestTime();

	/**
	 * Gets the flow passage time.
	 *
	 * @return the flow passage time
	 */
	 AttendanceTime getFlowPassageTime();
}
