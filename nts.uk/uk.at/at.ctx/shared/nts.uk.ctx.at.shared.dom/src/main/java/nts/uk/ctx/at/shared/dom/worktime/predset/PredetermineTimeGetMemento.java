/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;

/**
 * The Interface PredetermineTimeGetMemento.
 */
public interface PredetermineTimeGetMemento {
	
	/**
	 * Gets the adds the time.
	 *
	 * @return the adds the time
	 */
	BreakDownTimeDay getAddTime();
	
	
	/**
	 * Gets the pred time.
	 *
	 * @return the pred time
	 */
	BreakDownTimeDay getPredTime();

}
