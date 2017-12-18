/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlHalfDayWtzPolicyImpl.
 */
@Stateless
public class FlHalfDayWtzPolicyImpl implements FlHalfDayWtzPolicy {

	/** The flow rest policy. */
	@Inject
	private FlowWorkRestTimezonePolicy flowRestPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzPolicy#validate(nts
	 * .uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtz)
	 */
	@Override
	public void validate(PredetemineTimeSetting predTime, FlowHalfDayWorkTimezone flowHalf) {
		// validate FlowWorkRestTimezone
		this.flowRestPolicy.validate(predTime, flowHalf.getRestTimezone());
	}

}
