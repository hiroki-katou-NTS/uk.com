/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowRestSettingPolicy.
 */
public interface FlowRestSettingPolicy {
	
	/**
	 * Validate.
	 *
	 * @param flowRestSetting the flow rest setting
	 * @param predTime the pred time
	 */
	void validate(FlowRestSetting flowRestSetting, PredetemineTimeSetting predTime);
}
