/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowWorkRestTimezonePolicy.
 */
public interface FlowWorkRestTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param flowRest the flow rest
	 */
	void validate(PredetemineTimeSetting predTime, FlowWorkRestTimezone flowRest);
}
