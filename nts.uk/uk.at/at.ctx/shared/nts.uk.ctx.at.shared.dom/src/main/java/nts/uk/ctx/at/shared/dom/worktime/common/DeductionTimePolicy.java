/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface DeductionTimePolicy.
 */
public interface DeductionTimePolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param dedTime the ded time
	 * @return true, if successful
	 */
	void validate(PredetemineTimeSetting predTime, DeductionTime dedTime);
}
