/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;

/**
 * The Interface DiffTimezoneSettingPolicy.
 */
public interface DiffTimezoneSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param diffTzSet the diff tz set
	 * @param displayMode the display mode
	 * @param dayAtr the day atr
	 * @param useHalfDayShift the use half day shift
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, DiffTimezoneSetting diffTzSet, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift);

	/**
	 * Filter timezone.
	 *
	 * @param predTime the pred time
	 * @param origin the origin
	 * @param displayMode the display mode
	 * @param dayAtr the day atr
	 * @param useHalfDayShift the use half day shift
	 */
	void filterTimezone(PredetemineTimeSetting predTime, DiffTimezoneSetting origin, DisplayMode displayMode,
			AmPmAtr dayAtr, boolean useHalfDayShift);
}
