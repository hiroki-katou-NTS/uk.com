/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;

/**
 * The Interface OverTimeOfTimeZoneSetPolicy.
 */
public interface OverTimeOfTimeZoneSetPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param otSet the ot set
	 * @param displayMode the display mode
	 * @param dayAtr the day atr
	 * @param useHalfDayShift the use half day shift
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, OverTimeOfTimeZoneSet otSet, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift);
}
