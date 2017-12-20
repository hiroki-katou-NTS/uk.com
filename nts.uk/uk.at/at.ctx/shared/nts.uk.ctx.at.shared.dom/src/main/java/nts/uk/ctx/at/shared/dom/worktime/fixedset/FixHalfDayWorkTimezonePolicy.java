/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FixHalfDayWorkTimezonePolicy.
 */
public interface FixHalfDayWorkTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param fixHalfDay the fix half day
	 * @param predTime the pred time
	 */
	void validate(FixHalfDayWorkTimezone fixHalfDay, PredetemineTimeSetting predTime);
}
