/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface WorkTimezoneMedicalSetSetMemento.
 */
public interface WorkTimezoneMedicalSetSetMemento {

	/**
	 * Sets the rounding set.
	 *
	 * @param set the new rounding set
	 */
	 void setRoundingSet(TimeRoundingSetting set);

	/**
	 * Sets the work system atr.
	 *
	 * @param atr the new work system atr
	 */
	 void setWorkSystemAtr(WorkSystemAtr atr);

	/**
	 * Sets the application time.
	 *
	 * @param time the new application time
	 */
	 void setApplicationTime(OneDayTime time);
}
