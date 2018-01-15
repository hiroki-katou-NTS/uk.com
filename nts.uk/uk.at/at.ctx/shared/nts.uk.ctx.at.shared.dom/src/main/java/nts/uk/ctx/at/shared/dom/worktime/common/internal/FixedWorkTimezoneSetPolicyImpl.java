/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

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
	OverTimeOfTimeZoneSetPolicy otPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetPolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSet,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(FixedWorkTimezoneSet fixedWtz, PredetemineTimeSetting predTime) {
		// validate list emTimezone
		fixedWtz.getLstWorkingTimezone().forEach(wtz -> this.emTzPolicy.validate(predTime, wtz));

		fixedWtz.getLstOTTimezone().forEach(otz -> this.otPolicy.validate(predTime, otz));
	}
}
