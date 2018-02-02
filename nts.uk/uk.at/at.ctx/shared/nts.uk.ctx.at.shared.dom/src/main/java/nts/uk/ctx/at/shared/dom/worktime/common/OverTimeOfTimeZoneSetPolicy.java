/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface OverTimeOfTimeZoneSetPolicy.
 */
public interface OverTimeOfTimeZoneSetPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param otSet the ot set
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, OverTimeOfTimeZoneSet otSet);
}
