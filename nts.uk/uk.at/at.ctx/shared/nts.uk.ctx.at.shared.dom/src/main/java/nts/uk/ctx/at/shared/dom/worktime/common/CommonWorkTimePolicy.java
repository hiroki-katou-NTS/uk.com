/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface CommonWorkTimePolicy.
 */
public interface CommonWorkTimePolicy {

	/**
	 * Validate work time of timezone set.
	 *
	 * @param pred the pred
	 */
	public void validateWorkTimeOfTimezoneSet(PredetemineTimeSetting pred);
	
	/**
	 * Validate over time hour set.
	 *
	 * @param pred the pred
	 */
	public void validateOverTimeHourSet(PredetemineTimeSetting pred);
}
