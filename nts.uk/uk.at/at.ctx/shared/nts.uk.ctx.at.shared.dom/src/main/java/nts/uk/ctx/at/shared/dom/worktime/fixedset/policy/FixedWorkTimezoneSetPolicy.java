/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;

/**
 * The Interface FixedWorkTimezoneSetPolicy.
 */
public interface FixedWorkTimezoneSetPolicy {

	/**
	 * Validate fixed and diff.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param worktimeSet the worktime set
	 * @param displayMode the display mode
	 * @param dayAtr the day atr
	 * @param useHalfDayShift the use half day shift
	 */
	void validateFixedAndDiff(BundledBusinessException be, PredetemineTimeSetting predTime, FixedWorkTimezoneSet worktimeSet, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift);

	/**
	 * Validate flex.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param worktimeSet the worktime set
	 * @param displayMode the display mode
	 * @param dayAtr the day atr
	 * @param useHalfDayShift the use half day shift
	 */
	void validateFlex(BundledBusinessException be, PredetemineTimeSetting predTime, FixedWorkTimezoneSet worktimeSet, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift);

	/**
	 * Filter timezone.
	 *
	 * @param predTime the pred time
	 * @param origin the origin
	 * @param displayMode the display mode
	 * @param dayAtr the day atr
	 * @param useHalfDayShift the use half day shift
	 */
	void filterTimezone(PredetemineTimeSetting predTime, FixedWorkTimezoneSet origin, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift);
}
