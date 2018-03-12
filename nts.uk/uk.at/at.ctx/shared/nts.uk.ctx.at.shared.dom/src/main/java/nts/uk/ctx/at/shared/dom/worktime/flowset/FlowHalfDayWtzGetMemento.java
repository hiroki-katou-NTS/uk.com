/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Interface FlowHalfDayWorkTimezoneGetMemento.
 */
public interface FlowHalfDayWtzGetMemento {

	/**
	 * Gets the rest timezone.
	 *
	 * @return the rest timezone
	 */
	 FlowWorkRestTimezone getRestTimezone();

	/**
	 * Gets the work time zone.
	 *
	 * @return the work time zone
	 */
	 FlowWorkTimezoneSetting getWorkTimeZone();
}
