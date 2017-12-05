/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

/**
 * The Interface FixedWorkTimezoneSetSetMemento.
 */
public interface FixedWorkTimezoneSetSetMemento {
	
	/**
	 * Sets the lst working timezone.
	 *
	 * @param lstWorkingTimezone the new lst working timezone
	 */
	void setLstWorkingTimezone(List<EmTimeZoneSet> lstWorkingTimezone);
	
	
	/**
	 * Sets the lst OT timezone.
	 *
	 * @param lstOTTimezone the new lst OT timezone
	 */
	void setLstOTTimezone(List<OverTimeOfTimeZoneSet> lstOTTimezone);

}
