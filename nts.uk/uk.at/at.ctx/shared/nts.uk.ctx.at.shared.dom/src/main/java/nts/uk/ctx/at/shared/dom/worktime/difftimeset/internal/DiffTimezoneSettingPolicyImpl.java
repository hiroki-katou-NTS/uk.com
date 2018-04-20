/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimezoneSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;

/**
 * The Class DiffTimezoneSettingPolicyImpl.
 */
@Stateless
public class DiffTimezoneSettingPolicyImpl implements DiffTimezoneSettingPolicy {

	/** The em tz policy. */
	@Inject
	private EmTimeZoneSetPolicy emTzPolicy;

	/** The ot set policy. */
	@Inject
	private OverTimeOfTimeZoneSetPolicy otSetPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingPolicy#
	 * validate(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr, boolean)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, DiffTimezoneSetting diffTzSet,
			DisplayMode displayMode, AmPmAtr dayAtr, boolean useHalfDayShift) {
		// Validate list working timezone
		diffTzSet.getEmploymentTimezones().forEach(workingTimezone -> {
			this.emTzPolicy.validateFixedAndDiff(be, predTime, workingTimezone, displayMode, dayAtr, useHalfDayShift);
		});

		// Validate list OT timezone
		diffTzSet.getOTTimezones().forEach(workingTimezone -> {
			this.otSetPolicy.validate(be, predTime, workingTimezone, displayMode, dayAtr, useHalfDayShift);
		});
	}

}
