/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowRestTimezonePolicy.
 */
public interface FlowRestTimezonePolicy {
	
	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param flowRestTimezone the flow rest timezone
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowRestTimezone flowRestTimezone);
}