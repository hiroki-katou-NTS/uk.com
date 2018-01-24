/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowRestTimezonePolicy.
 */
public interface FlowRestTimezonePolicy {
	
	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param flowRestTimezone the flow rest timezone
	 */
	void validate(PredetemineTimeSetting predTime, FlowRestTimezone flowRestTimezone);
}