/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Interface DiffTimeWorkSettingPolicy.
 */
public interface DiffTimeWorkSettingPolicy {

	/**
	 * Validate.
	 *
	 * @param bundledBusinessExceptions the bundled business exceptions
	 * @param pred the pred
	 * @param displayMode the display mode
	 * @param diffTimeWorkSetting the diff time work setting
	 */
	void validate(BundledBusinessException bundledBusinessExceptions, PredetemineTimeSetting pred, WorkTimeDisplayMode displayMode, DiffTimeWorkSetting diffTimeWorkSetting);

	/**
	 * Filter timezone.
	 *
	 * @param pred the pred
	 * @param displayMode the display mode
	 * @param diffTimeWorkSetting the diff time work setting
	 */
	void filterTimezone(PredetemineTimeSetting pred, WorkTimeDisplayMode displayMode, DiffTimeWorkSetting diffTimeWorkSetting);
}
