/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimeHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimezoneSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Class DiffTimeHalfDayWorkTimezonePolicyImpl.
 */
@Stateless
public class DiffTimeHalfDayWorkTimezonePolicyImpl implements DiffTimeHalfDayWorkTimezonePolicy {

	/** The diff timezone policy. */
	@Inject
	private DiffTimezoneSettingPolicy diffTimezonePolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeHalfDayWorkTimezonePolicy#validate(nts.arc.error.
	 * BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeHalfDayWorkTimezone, boolean)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimeDisplayMode displayMode,
			DiffTimeHalfDayWorkTimezone halfDayWork, boolean isUseHalfDayShift) {
		this.diffTimezonePolicy.validate(be, predTime, halfDayWork.getWorkTimezone(), displayMode.getDisplayMode(),
				halfDayWork.getAmPmAtr(), isUseHalfDayShift);
	}

}
