/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlTimeSettingPolicy.
 */
public interface FlowTimeSettingPolicy {
	
	/**
	 * Validate.
	 *
	 * @param predTime the pred time
	 * @param flTimeSetting the fl time setting
	 */
	void validate(PredetemineTimeSetting predTime, FlowTimeSetting flTimeSetting);
}
