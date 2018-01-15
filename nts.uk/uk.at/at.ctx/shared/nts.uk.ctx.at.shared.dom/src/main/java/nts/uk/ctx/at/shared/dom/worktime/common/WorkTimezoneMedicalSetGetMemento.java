/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface WorkTimezoneMedicalSetGetMemento.
 */
public interface WorkTimezoneMedicalSetGetMemento {

	/**
	 * Gets the rounding set.
	 *
	 * @return the rounding set
	 */
	 TimeRoundingSetting getRoundingSet();

	/**
	 * Gets the work system atr.
	 *
	 * @return the work system atr
	 */
	 WorkSystemAtr getWorkSystemAtr();

	/**
	 * Gets the application time.
	 *
	 * @return the application time
	 */
	 OneDayTime getApplicationTime();
}
