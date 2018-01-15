/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface WorkTimezoneLateEarlySetPolicy.
 */
public interface WorkTimezoneLateEarlySetPolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param wtzLateEarly the wtz late early
	 */
	void validate (PredetemineTimeSetting predTime, WorkTimezoneLateEarlySet wtzLateEarly);
}
