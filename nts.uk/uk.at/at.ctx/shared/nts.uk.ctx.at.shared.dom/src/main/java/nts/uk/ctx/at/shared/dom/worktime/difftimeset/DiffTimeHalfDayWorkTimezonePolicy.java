/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface DiffTimeHalfDayWorkTimezonePolicy.
 */
public interface DiffTimeHalfDayWorkTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param diffTimeHalfDay the diff time half day
	 * @param predSet the pred set
	 */
	void validate(DiffTimeHalfDayWorkTimezone diffTimeHalfDay, PredetemineTimeSetting predSet);
}
