/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface TimeZoneRoundingPolicy.
 */
public interface TimeZoneRoundingPolicy {

	/**
	 * Validate range.
	 *
	 * @param predTime the pred time
	 * @param tzRounding the tz rounding
	 * @return true, if successful
	 */
	boolean validateRange(PredetemineTimeSetting predTime, TimeZoneRounding tzRounding);
}
