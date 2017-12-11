/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface EmTimeZoneSetPolicy.
 */
public interface EmTimeZoneSetPolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param etzSet the etz set
	 */
	void validate(PredetemineTimeSetting predTime, EmTimeZoneSet etzSet);
}
