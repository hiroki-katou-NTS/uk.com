/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface WorkTimezoneExtraordTimeSetSetMemento.
 */
public interface WorkTimezoneExtraordTimeSetSetMemento {

	/**
	 * Sets the holiday frame set.
	 *
	 * @param set the new holiday frame set
	 */
 	void  setHolidayFrameSet(HolidayFramset set);

	/**
	 * Sets the time rounding set.
	 *
	 * @param set the new time rounding set
	 */
	 void setTimeRoundingSet(TimeRoundingSetting set);

	/**
	 * Sets the OT frame set.
	 *
	 * @param set the new OT frame set
	 */
	 void setOTFrameSet(ExtraordWorkOTFrameSet set);

	/**
	 * Sets the calculate method.
	 *
	 * @param method the new calculate method
	 */
	 void setCalculateMethod(ExtraordTimeCalculateMethod method);
}
