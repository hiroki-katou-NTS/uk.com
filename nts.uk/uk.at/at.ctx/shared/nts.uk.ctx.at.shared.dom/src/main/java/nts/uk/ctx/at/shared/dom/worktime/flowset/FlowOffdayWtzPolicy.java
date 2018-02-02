/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowOffdayWtzPolicy.
 */
public interface FlowOffdayWtzPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param flowOff the flow off
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowOffdayWorkTimezone flowOff);
}
