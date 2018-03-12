/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import nts.arc.error.BundledBusinessException;

/**
 * The Interface WorkTimeSettingPolicy.
 */
public interface WorkTimeSettingPolicy {

	/**
	 * Validate exist.
	 *
	 * @param bundledBusinessExceptions the bundled business exceptions
	 * @param wtSet the wt set
	 */
	void validateExist(BundledBusinessException bundledBusinessExceptions, WorkTimeSetting wtSet);
}
