/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface DiffTimeHalfDayWorkTimezonePolicy.
 */
public interface DiffTimeHalfDayWorkTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param diffTimeHalfDay the diff time half day
	 * @param predSet the pred set
	 */
	void validate(BundledBusinessException be, DiffTimeHalfDayWorkTimezone diffTimeHalfDay, PredetemineTimeSetting predSet);
}
