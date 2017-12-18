/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;

/**
 * The Interface FlowOffdayWorkTimezoneSetMemento.
 */
public interface FlOffdayWtzSetMemento {

	/**
	 * Sets the rest time zone.
	 *
	 * @param tzone the new rest time zone
	 */
	void setRestTimeZone(FlowWorkRestTimezone tzone);

	/**
	 * Sets the lst work timezone.
	 *
	 * @param listHdtz the new lst work timezone
	 */
	void setLstWorkTimezone(List<FlowWorkHolidayTimeZone> listHdtz);
}
