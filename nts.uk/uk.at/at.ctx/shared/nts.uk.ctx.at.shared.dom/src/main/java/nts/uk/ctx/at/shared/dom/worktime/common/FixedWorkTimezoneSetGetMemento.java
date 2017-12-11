/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

/**
 * The Interface FixedWorkTimezoneSetGetMemento.
 */
public interface FixedWorkTimezoneSetGetMemento {

	/**
	 * Gets the lst working timezone.
	 *
	 * @return the lst working timezone
	 */
	List<EmTimeZoneSet> getLstWorkingTimezone();
	
	
	/**
	 * Gets the lst OT timezone.
	 *
	 * @return the lst OT timezone
	 */
	List<OverTimeOfTimeZoneSet> getLstOTTimezone();
}
