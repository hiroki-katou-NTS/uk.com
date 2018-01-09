/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface CoreTimeSettingPolicy.
 */
public interface CoreTimeSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param coreTimeSetting the core time setting
	 * @param predTime the pred time
	 */
	void validate(CoreTimeSetting coreTimeSetting, PredetemineTimeSetting predTime);
}
