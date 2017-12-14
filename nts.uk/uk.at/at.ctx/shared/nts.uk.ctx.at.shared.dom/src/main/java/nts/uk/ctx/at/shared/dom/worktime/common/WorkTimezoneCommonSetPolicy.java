/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface WorkTimezoneCommonSetPolicy.
 */
public interface WorkTimezoneCommonSetPolicy {

	/**
	 * Validate.
	 *
	 * @param predSet the pred set
	 * @param wtzCommon the wtz common
	 */
	void validate(PredetemineTimeSetting predSet, WorkTimezoneCommonSet wtzCommon);
}
