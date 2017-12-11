/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FixedWorkSettingPolicy.
 */
public interface FixedWorkSettingPolicy {

	/**
	 * Can register.
	 *
	 * @param fixedWork the fixed work
	 * @param predTime the pred time
	 */
	void canRegister(FixedWorkSetting fixedWork, PredetemineTimeSetting predTime);
}
