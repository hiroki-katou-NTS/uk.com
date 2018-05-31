/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowOffdayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowOffdayWtzPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkHolidayTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlowOffdayWtzPolicyImpl.
 */
@Stateless
public class FlowOffdayWtzPolicyImpl implements FlowOffdayWtzPolicy {

	/** The flow rest policy. */
	@Inject
	private FlowWorkRestTimezonePolicy flowRestPolicy;
	
	/** The flow holiday policy. */
	@Inject
	private FlowWorkHolidayTimezonePolicy flowHolidayPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzPolicy#validate(nts.
	 * uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtz)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowOffdayWorkTimezone flowOff) {
		// validate FlowWorkRestTimezone
		this.flowRestPolicy.validate(be, predTime, flowOff.getRestTimeZone());
		
		flowOff.getLstWorkTimezone().forEach(timezone -> {
			this.flowHolidayPolicy.validate(be, predTime, timezone);
		});		
	}

}
