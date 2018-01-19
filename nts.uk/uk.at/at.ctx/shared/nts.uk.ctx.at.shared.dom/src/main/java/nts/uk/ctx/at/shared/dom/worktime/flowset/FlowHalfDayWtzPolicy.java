/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlHalfDayWtzPolicy.
 */
public interface FlowHalfDayWtzPolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param flowHalf the flow half
	 */
	void validate(PredetemineTimeSetting predTime, FlowHalfDayWorkTimezone flowHalf);
}
