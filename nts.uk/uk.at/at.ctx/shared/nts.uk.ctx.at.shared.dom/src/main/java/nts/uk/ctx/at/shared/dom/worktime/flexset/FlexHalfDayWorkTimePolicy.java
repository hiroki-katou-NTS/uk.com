/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlexHalfDayWorkTimePolicy.
 */
public interface FlexHalfDayWorkTimePolicy {

	/**
	 * Validate.
	 *
	 * @param flexHalfDay the flex half day
	 * @param predTime the pred time
	 */
	void validate(FlexHalfDayWorkTime flexHalfDay, PredetemineTimeSetting predTime);
}
