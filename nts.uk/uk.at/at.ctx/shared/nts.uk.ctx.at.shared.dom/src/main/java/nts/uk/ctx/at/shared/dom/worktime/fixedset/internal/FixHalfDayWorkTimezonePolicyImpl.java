/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

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
	public void validate(FixedWorkSetting fixedWorkSetting, PredetemineTimeSetting predTime) {
		fixedWorkSetting.getLstHalfDayWorkTimezone().forEach(item -> this.fixedWtzPolicy
				.validate(item.getWorkTimezone(), predTime));
	}
}
