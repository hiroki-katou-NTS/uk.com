/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;

/**
 * The Interface PredetermineTimeSetMemento.
 */
public interface PredetermineTimeSetMemento {

	/**
	 * Sets the adds the time.
	 *
	 * @param addTime the new adds the time
	 */
	void setAddTime(BreakDownTimeDay addTime);
	
	/**
	 * Sets the pred time.
	 *
	 * @param predTime the new pred time
	 */
	void setPredTime(BreakDownTimeDay predTime);
}
