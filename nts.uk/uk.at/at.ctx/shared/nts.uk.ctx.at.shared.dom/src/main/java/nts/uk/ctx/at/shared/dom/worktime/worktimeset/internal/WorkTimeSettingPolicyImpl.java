/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

/**
 * The Class WorkTimeSettingPolicyImpl.
 */
@Stateless
public class WorkTimeSettingPolicyImpl implements WorkTimeSettingPolicy {

	/** The repo. */
	@Inject
	private WorkTimeSettingRepository repo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingPolicy#
	 * validateExist(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting)
	 */
	@Override
	public void validateExist(BundledBusinessException bundledBusinessExceptions, WorkTimeSetting wtSet) {
		if (this.isExisted(wtSet)) {
			bundledBusinessExceptions.addMessage("Msg_3");
		}
	}

	/**
	 * Checks if is existed.
	 *
	 * @param wtSet
	 *            the wt set
	 * @return true, if is existed
	 */
	private boolean isExisted(WorkTimeSetting wtSet) {
		Optional<WorkTimeSetting> optWtSet = this.repo.findByCode(wtSet.getCompanyId(), wtSet.getWorktimeCode().v());
		return optWtSet.isPresent();
	}
}
