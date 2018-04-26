/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.FixedWorkTimezoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;

/**
 * The Class FixedWorkTimezoneSetPolicyImpl.
 */
@Stateless
public class FixedWorkTimezoneSetPolicyImpl implements FixedWorkTimezoneSetPolicy {

	/** The em tz policy. */
	@Inject
	private EmTimeZoneSetPolicy emTzPolicy;

	/** The ot policy. */
	@Inject
	private OverTimeOfTimeZoneSetPolicy otPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetPolicy#
	 * validate(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr, boolean)
	 */
	@Override
	public void validateFixedAndDiff(BundledBusinessException be, PredetemineTimeSetting predTime,
			FixedWorkTimezoneSet worktimeSet, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift) {
		// Validate list working timezone
		worktimeSet.getLstWorkingTimezone().forEach(workingTimezone -> {
			this.emTzPolicy.validateFixedAndDiff(be, predTime, workingTimezone, displayMode, dayAtr, useHalfDayShift);
		});

		// Validate list OT timezone
		worktimeSet.getLstOTTimezone().forEach(workingTimezone -> {
			this.otPolicy.validate(be, predTime, workingTimezone, displayMode, dayAtr, useHalfDayShift);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.
	 * FixedWorkTimezoneSetPolicy#validateFlex(nts.arc.error.
	 * BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr, boolean)
	 */
	@Override
	public void validateFlex(BundledBusinessException be, PredetemineTimeSetting predTime,
			FixedWorkTimezoneSet worktimeSet, DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift) {
		// Validate list working timezone
		worktimeSet.getLstWorkingTimezone().forEach(workingTimezone -> {
			this.emTzPolicy.validateFlex(be, predTime, workingTimezone, displayMode, dayAtr, useHalfDayShift);
		});

		// Validate list OT timezone
		worktimeSet.getLstOTTimezone().forEach(workingTimezone -> {
			this.otPolicy.validate(be, predTime, workingTimezone, displayMode, dayAtr, useHalfDayShift);
		});
	}
}
