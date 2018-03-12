/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FixedWorkTimezoneSetPolicy.
 */
public interface FixedWorkTimezoneSetPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param fixedWtz the fixed wtz
	 * @param predTime the pred time
	 */
	void validate(BundledBusinessException be, FixedWorkTimezoneSet fixedWtz, PredetemineTimeSetting predTime);
}
