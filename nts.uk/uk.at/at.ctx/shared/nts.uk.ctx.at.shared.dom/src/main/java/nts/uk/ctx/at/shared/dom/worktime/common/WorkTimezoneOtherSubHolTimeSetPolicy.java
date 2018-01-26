/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface WorkTimezoneOtherSubHolTimeSetPolicy.
 */
public interface WorkTimezoneOtherSubHolTimeSetPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predSet the pred set
	 * @param otherSubHdSet the other sub hd set
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predSet, WorkTimezoneOtherSubHolTimeSet otherSubHdSet);
}
