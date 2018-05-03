/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezone;
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
	public void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting, FlowStampReflectTimezone flowStampReflectTimezone);
}
