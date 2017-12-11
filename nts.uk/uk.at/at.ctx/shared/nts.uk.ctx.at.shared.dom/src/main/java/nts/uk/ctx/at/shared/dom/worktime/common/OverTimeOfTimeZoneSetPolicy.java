/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface OverTimeOfTimeZoneSetPolicy.
 */
public interface OverTimeOfTimeZoneSetPolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param otSet the ot set
	 */
	void validate(PredetemineTimeSetting predTime, OverTimeOfTimeZoneSet otSet);
}
