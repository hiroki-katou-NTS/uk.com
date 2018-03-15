/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowHalfDayWtzPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlowHalfDayWtzPolicyImpl.
 */
@Stateless
public class FlowHalfDayWtzPolicyImpl implements FlowHalfDayWtzPolicy {

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
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowHalfDayWorkTimezone flowHalf) {
		// validate FlowWorkRestTimezone
		this.flowRestPolicy.validate(be, predTime, flowHalf.getRestTimezone());
	}

}
