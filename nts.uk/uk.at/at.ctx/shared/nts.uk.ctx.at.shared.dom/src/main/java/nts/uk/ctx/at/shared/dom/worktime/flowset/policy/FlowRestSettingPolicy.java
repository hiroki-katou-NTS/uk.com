/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowRestSettingPolicy.
 */
public interface FlowRestSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param flowRestSetting the flow rest setting
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowRestSetting flowRestSetting);
}
