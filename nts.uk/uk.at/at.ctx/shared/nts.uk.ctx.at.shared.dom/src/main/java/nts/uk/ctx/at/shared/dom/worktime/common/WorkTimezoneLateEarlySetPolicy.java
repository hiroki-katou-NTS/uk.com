/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface WorkTimezoneLateEarlySetPolicy.
 */
public interface WorkTimezoneLateEarlySetPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param wtzLateEarly the wtz late early
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimezoneLateEarlySet wtzLateEarly);
}
