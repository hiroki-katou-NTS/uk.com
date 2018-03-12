/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface CommonWorkTimePolicy.
 */
public interface CommonWorkTimePolicy {

	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param pred the pred
	 * @param workTimezoneCommonSet the work timezone common set
	 */
	public void validate(BundledBusinessException be, PredetemineTimeSetting pred,WorkTimezoneCommonSet workTimezoneCommonSet);
}
