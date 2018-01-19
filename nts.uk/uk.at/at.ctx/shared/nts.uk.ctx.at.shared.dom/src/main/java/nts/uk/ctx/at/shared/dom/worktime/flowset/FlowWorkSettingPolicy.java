/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowWorkSettingPolicy.
 */
public interface FlowWorkSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param bundledExceptions the bundled exceptions
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param flowWorkSetting the flow work setting
	 */
	void validate(PredetemineTimeSetting predetemineTimeSetting, FlowWorkSetting flowWorkSetting);
}
