/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlexStampReflectTimezonePolicy.
 */
public interface FlexStampReflectTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param flexWorkSetting the flex work setting
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting, FlexWorkSetting flexWorkSetting);
}
