/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface DiffTimezoneSettingPolicy.
 */
public interface DiffTimezoneSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param diffTzSet the diff tz set
	 * @param predSet the pred set
	 */
	void validate(DiffTimezoneSetting diffTzSet, PredetemineTimeSetting predSet);
}
