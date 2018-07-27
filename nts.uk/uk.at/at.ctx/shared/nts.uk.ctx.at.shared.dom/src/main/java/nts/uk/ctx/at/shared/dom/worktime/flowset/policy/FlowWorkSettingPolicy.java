/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Interface FlowWorkSettingPolicy.
 */
public interface FlowWorkSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param bundledBusinessExceptions the bundled business exceptions
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param displayMode the display mode
	 * @param flowWorkSetting the flow work setting
	 */
	void validate(BundledBusinessException bundledBusinessExceptions, PredetemineTimeSetting predetemineTimeSetting, WorkTimeDisplayMode displayMode, FlowWorkSetting flowWorkSetting);
}
