/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FixedWorkSettingPolicy.
 */
public interface FixedWorkSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predTime the pred time
	 * @param fixedWork the fixed work
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FixedWorkSetting fixedWork);
}
