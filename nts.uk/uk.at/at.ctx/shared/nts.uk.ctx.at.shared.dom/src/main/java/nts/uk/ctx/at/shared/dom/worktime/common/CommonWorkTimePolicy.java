/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface CommonWorkTimePolicy.
 */
public interface CommonWorkTimePolicy {

	/**
	 * Validate.
	 *
	 * @param pred the pred
	 * @param workTimezoneCommonSet the work timezone common set
	 */
	public void validate(PredetemineTimeSetting pred,WorkTimezoneCommonSet workTimezoneCommonSet);
}
