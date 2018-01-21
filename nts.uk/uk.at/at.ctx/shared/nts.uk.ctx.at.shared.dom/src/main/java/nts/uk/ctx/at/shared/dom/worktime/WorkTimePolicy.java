/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime;

import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * The Interface WorkTimePolicy.
 */
public interface WorkTimePolicy {

	/**
	 * Validate fixed work setting.
	 *
	 * @param workTimeSetting the work time setting
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param fixedWorkSetting the fixed work setting
	 */
	void validateFixedWorkSetting(WorkTimeSetting workTimeSetting, PredetemineTimeSetting predetemineTimeSetting, FixedWorkSetting fixedWorkSetting);

	/**
	 * Validate diff time work setting.
	 *
	 * @param workTimeSetting the work time setting
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param diffTimeWorkSetting the diff time work setting
	 */
	void validateDiffTimeWorkSetting(WorkTimeSetting workTimeSetting, PredetemineTimeSetting predetemineTimeSetting, DiffTimeWorkSetting diffTimeWorkSetting);

	/**
	 * Validate flow work setting.
	 *
	 * @param workTimeSetting the work time setting
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param flowWorkSetting the flow work setting
	 */
	void validateFlowWorkSetting(WorkTimeSetting workTimeSetting, PredetemineTimeSetting predetemineTimeSetting, FlowWorkSetting flowWorkSetting);

	/**
	 * Validate flex work setting.
	 *
	 * @param workTimeSetting the work time setting
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param flexWorkSetting the flex work setting
	 */
	void validateFlexWorkSetting(WorkTimeSetting workTimeSetting, PredetemineTimeSetting predetemineTimeSetting, FlexWorkSetting flexWorkSetting);

}
