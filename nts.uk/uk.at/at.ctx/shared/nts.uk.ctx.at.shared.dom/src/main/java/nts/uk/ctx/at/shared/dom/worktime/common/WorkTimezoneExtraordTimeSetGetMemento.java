/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface WorkTimezoneExtraordTimeSetGetMemento.
 */
public interface WorkTimezoneExtraordTimeSetGetMemento {

	/**
	 * Gets the holiday frame set.
	 *
	 * @return the holiday frame set
	 */
 	HolidayFramset getHolidayFrameSet();

	/**
	 * Gets the time rounding set.
	 *
	 * @return the time rounding set
	 */
	 TimeRoundingSetting getTimeRoundingSet();

	/**
	 * Gets the OT frame set.
	 *
	 * @return the OT frame set
	 */
	 ExtraordWorkOTFrameSet getOTFrameSet();

	/**
	 * Gets the calculate method.
	 *
	 * @return the calculate method
	 */
	 ExtraordTimeCalculateMethod getCalculateMethod();
}
