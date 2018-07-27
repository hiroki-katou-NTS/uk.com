/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Interface GoOutTimeRoundingSettingSetMemento.
 */
public interface GoOutTimeRoundingSettingSetMemento {
	
	
	/**
	 * Sets the rounding method.
	 *
	 * @param roundingMethod the new rounding method
	 */
	void setRoundingMethod(RoundingGoOutTimeSheet roundingMethod);
	
	/**
	 * Sets the rounding setting.
	 *
	 * @param roundingSetting the new rounding setting
	 */
	void setRoundingSetting(TimeRoundingSetting roundingSetting);

	
}
