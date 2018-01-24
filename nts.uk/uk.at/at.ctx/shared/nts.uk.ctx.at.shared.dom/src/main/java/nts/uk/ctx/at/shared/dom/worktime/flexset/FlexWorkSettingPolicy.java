/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlexWorkSettingPolicy.
 */
public interface FlexWorkSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predetemineTimeSet the predetemine time set
	 * @param flexWorkSetting the flex work setting
	 */
	public void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSet, FlexWorkSetting flexWorkSetting);

}
