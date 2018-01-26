/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface DiffTimezoneSettingPolicy.
 */
public interface DiffTimezoneSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param diffTzSet the diff tz set
	 * @param predSet the pred set
	 */
	void validate(BundledBusinessException be, DiffTimezoneSetting diffTzSet, PredetemineTimeSetting predSet);
}
