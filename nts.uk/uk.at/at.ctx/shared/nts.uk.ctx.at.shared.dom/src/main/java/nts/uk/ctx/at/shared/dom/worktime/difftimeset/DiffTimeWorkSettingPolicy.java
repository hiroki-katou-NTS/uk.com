/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Interface DiffTimeWorkSettingPolicy.
 */
public interface DiffTimeWorkSettingPolicy {

	/**
	  * Validate.
	  *
	  * @param bundledBusinessExceptions the bundled business exceptions
	  * @param pred the pred
	  * @param diffTimeWorkSetting the diff time work setting
	  */
	void validate(BundledBusinessException bundledBusinessExceptions, PredetemineTimeSetting pred, DiffTimeWorkSetting diffTimeWorkSetting);
}
