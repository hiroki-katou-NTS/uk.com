/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FixedWorkTimezoneSetPolicy.
 */
public interface FixedWorkTimezoneSetPolicy {

	/**
	 * Validate.
	 *
	 * @param fixedWtz the fixed wtz
	 * @param predTime the pred time
	 */
	void validate(FixedWorkTimezoneSet fixedWtz, PredetemineTimeSetting predTime);
}
