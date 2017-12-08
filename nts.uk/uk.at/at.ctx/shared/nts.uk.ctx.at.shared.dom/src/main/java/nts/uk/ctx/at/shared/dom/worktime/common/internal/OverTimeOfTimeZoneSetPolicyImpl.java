/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class OverTimeOfTimeZoneSetPolicyImpl.
 */
@Stateless
public class OverTimeOfTimeZoneSetPolicyImpl implements OverTimeOfTimeZoneSetPolicy {

	/** The tzr policy. */
	@Inject
	private TimeZoneRoundingPolicy tzrPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy#validate(nts
	 * .uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet)
	 */
	@Override
	public void validate(PredetemineTimeSetting predTime, OverTimeOfTimeZoneSet otSet) {
		this.tzrPolicy.validateRange(predTime, otSet.getTimezone());
	}
}
