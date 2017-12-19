/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlOffdayWtzPolicyImpl.
 */
@Stateless
public class FlOffdayWtzPolicyImpl implements FlOffdayWtzPolicy {

	/** The flow rest policy. */
	@Inject
	private FlowWorkRestTimezonePolicy flowRestPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzPolicy#validate(nts.
	 * uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtz)
	 */
	@Override
	public void validate(PredetemineTimeSetting predTime, FlowOffdayWorkTimezone flowOff) {
		// validate FlowWorkRestTimezone
		this.flowRestPolicy.validate(predTime, flowOff.getRestTimeZone());
	}

}
