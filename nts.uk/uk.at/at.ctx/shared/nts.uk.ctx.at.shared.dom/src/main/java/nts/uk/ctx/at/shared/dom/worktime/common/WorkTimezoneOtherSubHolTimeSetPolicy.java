/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface WorkTimezoneOtherSubHolTimeSetPolicy.
 */
public interface WorkTimezoneOtherSubHolTimeSetPolicy {

	/**
	 * Validate.
	 *
	 * @param predSet the pred set
	 * @param otherSubHdSet the other sub hd set
	 */
	void validate(PredetemineTimeSetting predSet, WorkTimezoneOtherSubHolTimeSet otherSubHdSet);
}
