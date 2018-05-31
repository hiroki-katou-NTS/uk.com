/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;

/**
 * The Interface EmTimeZoneSetPolicy.
 */
public interface EmTimeZoneSetPolicy {

	/**
	 * Validate fixed and diff.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param etzSet the etz set
	 * @param displayMode the display mode
	 * @param dayAtr the day atr
	 * @param useHalfDayShift the use half day shift
	 */
	void validateFixedAndDiff(BundledBusinessException be, PredetemineTimeSetting predTime, EmTimeZoneSet etzSet, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift);

	/**
	 * Validate flex.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param etzSet the etz set
	 * @param displayMode the display mode
	 * @param dayAtr the day atr
	 * @param useHalfDayShift the use half day shift
	 */
	void validateFlex(BundledBusinessException be, PredetemineTimeSetting predTime, EmTimeZoneSet etzSet, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift);
}
