/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface FlowStampReflectTimezonePolicy.
 */
public interface FlowStampReflectTimezonePolicy {

	/**
	 * Validate.
	 *
	 * @param bundledExceptions the bundled exceptions
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param flowStampReflectTimezone the flow stamp reflect timezone
	 */
	public void validate(PredetemineTimeSetting predetemineTimeSetting, FlowStampReflectTimezone flowStampReflectTimezone);
}
