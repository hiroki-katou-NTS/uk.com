/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlWorkSettingPolicy.
 */
public interface FlWorkSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param flowSet the flow set
	 * @param predSet the pred set
	 */
	void validate(FlowWorkSetting flowSet, PredetemineTimeSetting predSet);
}
