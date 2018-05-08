/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Interface FlexWorkSettingPolicy.
 */
public interface FlexWorkSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param displayMode the display mode
	 * @param flexWork the flex work
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimeDisplayMode displayMode, FlexWorkSetting flexWork);

}
