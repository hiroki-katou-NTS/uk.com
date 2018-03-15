/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowTimeSettingPolicy.
 */
public interface FlowTimeSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param flowTimeSetting the flow time setting
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting, FlowTimeSetting flowTimeSetting);
}
