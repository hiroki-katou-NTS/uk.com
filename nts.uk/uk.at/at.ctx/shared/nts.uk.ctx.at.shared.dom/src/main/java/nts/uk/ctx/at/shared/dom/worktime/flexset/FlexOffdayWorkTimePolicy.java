/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlexOffdayWorkTimePolicy.
 */
public interface FlexOffdayWorkTimePolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param flexOffDay the flex off day
	 */
	void validate(PredetemineTimeSetting predTime, FlexOffdayWorkTime flexOffDay);
}
