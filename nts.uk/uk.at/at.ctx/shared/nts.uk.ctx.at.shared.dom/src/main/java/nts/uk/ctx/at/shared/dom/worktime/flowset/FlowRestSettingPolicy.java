/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowRestSettingPolicy.
 */
public interface FlowRestSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param flowRestSetting the flow rest setting
	 */
	void validate(PredetemineTimeSetting predTime, FlowRestSetting flowRestSetting);
}
