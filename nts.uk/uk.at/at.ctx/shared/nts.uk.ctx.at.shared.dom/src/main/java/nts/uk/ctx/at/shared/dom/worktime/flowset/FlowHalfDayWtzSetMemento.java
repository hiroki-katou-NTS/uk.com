/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

/**
 * The Interface FlowHalfDayWorkTimezoneSetMemento.
 */
public interface FlowHalfDayWtzSetMemento {

	/**
	 * Sets the rest timezone.
	 *
	 * @param tzone the new rest timezone
	 */
	 void setRestTimezone(FlowWorkRestTimezone tzone);

	/**
	 * Sets the work time zone.
	 *
	 * @param tzone the new work time zone
	 */
	 void setWorkTimeZone(FlowWorkTimezoneSetting tzone);
}
