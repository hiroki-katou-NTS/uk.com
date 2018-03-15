/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Interface FixedWorkSettingPolicy.
 */
public interface FixedWorkSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param displayMode the display mode
	 * @param fixedWork the fixed work
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimeDisplayMode displayMode, FixedWorkSetting fixedWork);
}
