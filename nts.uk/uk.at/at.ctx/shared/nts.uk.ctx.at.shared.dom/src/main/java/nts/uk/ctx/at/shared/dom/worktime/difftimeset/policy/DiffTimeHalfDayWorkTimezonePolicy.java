/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Interface DiffTimeHalfDayWorkTimezonePolicy.
 */
public interface DiffTimeHalfDayWorkTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param displayMode the display mode
	 * @param halfDayWork the half day work
	 * @param isUseHalfDayShift the is use half day shift
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimeDisplayMode displayMode, DiffTimeHalfDayWorkTimezone halfDayWork, boolean isUseHalfDayShift);

	/**
	 * Filter timezone.
	 *
	 * @param predTime the pred time
	 * @param origin the origin
	 * @param displayMode the display mode
	 * @param useHalfDayShift the use half day shift
	 */
	void filterTimezone(PredetemineTimeSetting predTime, DiffTimeHalfDayWorkTimezone origin, DisplayMode displayMode, boolean useHalfDayShift);
}
