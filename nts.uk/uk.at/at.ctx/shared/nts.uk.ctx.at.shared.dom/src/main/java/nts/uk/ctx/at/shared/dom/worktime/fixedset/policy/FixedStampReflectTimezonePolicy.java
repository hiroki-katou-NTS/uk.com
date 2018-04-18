/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FixedStampReflectTimezonePolicy.
 */
public interface FixedStampReflectTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param fixedWorkSetting the fixed work setting
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting, FixedWorkSetting fixedWorkSetting);
}
