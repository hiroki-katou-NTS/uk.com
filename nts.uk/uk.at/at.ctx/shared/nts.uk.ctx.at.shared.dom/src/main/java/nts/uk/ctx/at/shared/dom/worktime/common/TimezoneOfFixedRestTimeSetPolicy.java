/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface TimezoneOfFixedRestTimeSetPolicy.
 */
public interface TimezoneOfFixedRestTimeSetPolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param fixedRest the fixed rest
	 */
	void validate(PredetemineTimeSetting predTime, TimezoneOfFixedRestTimeSet fixedRest);
}
