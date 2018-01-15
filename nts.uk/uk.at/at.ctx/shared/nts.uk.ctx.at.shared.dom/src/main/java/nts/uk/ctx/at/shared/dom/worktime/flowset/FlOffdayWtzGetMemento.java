/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;

/**
 * The Interface FlowOffdayWorkTimezoneGetMemento.
 */
public interface FlOffdayWtzGetMemento {

	/**
	 * Gets the rest time zone.
	 *
	 * @return the rest time zone
	 */
	FlowWorkRestTimezone getRestTimeZone();

	/**
	 * Gets the lst work timezone.
	 *
	 * @return the lst work timezone
	 */
	List<FlowWorkHolidayTimeZone> getLstWorkTimezone();
}
