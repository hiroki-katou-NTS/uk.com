/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface StampReflectTimezonePolicy.
 */
public interface StampReflectTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param bundledBusinessExceptions the bundled business exceptions
	 * @param isFlow the is flow
	 * @param timezone the timezone
	 */
	void validate(BundledBusinessException bundledBusinessExceptions, boolean isFlow, StampReflectTimezone timezone,PredetemineTimeSetting predetemineTimeSetting);
}
