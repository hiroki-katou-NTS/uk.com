/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface DiffTimeStampReflectTimezonePolicy.
 */
public interface DiffTimeStampReflectTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param diffTimeWorkSetting the diff time work setting
	 */
	void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting, DiffTimeWorkSetting diffTimeWorkSetting);
}
