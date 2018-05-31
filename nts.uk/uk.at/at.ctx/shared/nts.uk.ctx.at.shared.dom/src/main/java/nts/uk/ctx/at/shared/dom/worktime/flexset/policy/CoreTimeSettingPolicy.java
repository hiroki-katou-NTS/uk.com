/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface CoreTimeSettingPolicy.
 */
public interface CoreTimeSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param coreTimeSetting the core time setting
	 * @param predTime the pred time
	 */
	void validate(BundledBusinessException be, CoreTimeSetting coreTimeSetting, PredetemineTimeSetting predTime);
}
