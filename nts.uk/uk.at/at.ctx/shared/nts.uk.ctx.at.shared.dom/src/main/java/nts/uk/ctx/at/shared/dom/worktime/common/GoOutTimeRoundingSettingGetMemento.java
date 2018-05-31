/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface GoOutTimeRoundingSettingGetMemento.
 */
public interface GoOutTimeRoundingSettingGetMemento {
	
	/**
	 * Gets the rounding method.
	 *
	 * @return the rounding method
	 */
	RoundingGoOutTimeSheet getRoundingMethod();
	
	
	/**
	 * Gets the rounding setting.
	 *
	 * @return the rounding setting
	 */
	TimeRoundingSetting getRoundingSetting();

}
