/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Class FixHalfDayWorkTimezonePolicyImpl.
 */
@Stateless
public class FixHalfDayWorkTimezonePolicyImpl implements FixHalfDayWorkTimezonePolicy {

	/** The fixed wtz policy. */
	@Inject
	private FixedWorkTimezoneSetPolicy fixedWtzPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezonePolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * FixHalfDayWorkTimezone,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimeDisplayMode displayMode,
			FixHalfDayWorkTimezone halfDayWork, boolean isUseHalfDayShift) {
		this.fixedWtzPolicy.validate(be, predTime, halfDayWork.getWorkTimezone(), displayMode.getDisplayMode(),
				halfDayWork.getDayAtr(), isUseHalfDayShift);
	}
}
