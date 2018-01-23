/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.internal;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * The Class WorkTimePolicyImpl.
 */
@Stateless
public class WorkTimePolicyImpl implements WorkTimePolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.WorkTimePolicy#validateFixedWorkSetting
	 * (nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting)
	 */
	@Override
	public void validateFixedWorkSetting(WorkTimeSetting workTimeSetting, PredetemineTimeSetting predetemineTimeSetting,
			FixedWorkSetting fixedWorkSetting) {
		BundledBusinessException bundledExceptions = BundledBusinessException.newInstance();

		// TODO Auto-generated method stub

		if (CollectionUtil.isEmpty(bundledExceptions.cloneExceptions())) {
			bundledExceptions.throwExceptions();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.WorkTimePolicy#
	 * validateDiffTimeWorkSetting(nts.uk.ctx.at.shared.dom.worktime.worktimeset
	 * .WorkTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting)
	 */
	@Override
	public void validateDiffTimeWorkSetting(WorkTimeSetting workTimeSetting,
			PredetemineTimeSetting predetemineTimeSetting, DiffTimeWorkSetting diffTimeWorkSetting) {
		BundledBusinessException bundledExceptions = BundledBusinessException.newInstance();

		// TODO Auto-generated method stub

		if (CollectionUtil.isEmpty(bundledExceptions.cloneExceptions())) {
			bundledExceptions.throwExceptions();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.WorkTimePolicy#validateFlowWorkSetting(
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void validateFlowWorkSetting(WorkTimeSetting workTimeSetting, PredetemineTimeSetting predetemineTimeSetting,
			FlowWorkSetting flowWorkSetting) {
		BundledBusinessException bundledExceptions = BundledBusinessException.newInstance();

		// TODO Auto-generated method stub

		if (CollectionUtil.isEmpty(bundledExceptions.cloneExceptions())) {
			bundledExceptions.throwExceptions();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.WorkTimePolicy#validateFlexWorkSetting(
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting)
	 */
	@Override
	public void validateFlexWorkSetting(WorkTimeSetting workTimeSetting, PredetemineTimeSetting predetemineTimeSetting,
			FlexWorkSetting flexWorkSetting) {
		BundledBusinessException bundledExceptions = BundledBusinessException.newInstance();

		// TODO Auto-generated method stub

		if (CollectionUtil.isEmpty(bundledExceptions.cloneExceptions())) {
			bundledExceptions.throwExceptions();
		}
	}

}
