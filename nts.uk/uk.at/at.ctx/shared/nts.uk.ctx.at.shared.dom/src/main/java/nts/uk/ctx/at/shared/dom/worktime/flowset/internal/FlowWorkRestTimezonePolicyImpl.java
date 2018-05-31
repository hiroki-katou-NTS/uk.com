/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlowWorkRestTimezonePolicyImpl.
 */
@Stateless
public class FlowWorkRestTimezonePolicyImpl implements FlowWorkRestTimezonePolicy {

	/** The flow rest timezone policy. */
	@Inject
	private FlowRestTimezonePolicy flowRestTimezonePolicy;

	/** The fixed rest policy. */
	@Inject
	private TimezoneOfFixedRestTimeSetPolicy fixedRestPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezonePolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowWorkRestTimezone flowRest) {

		this.flowRestTimezonePolicy.validate(be, predTime, flowRest.getFlowRestTimezone());

		// validate TimezoneOfFixedRestTimeSet
		if (flowRest.isFixRestTime()) {
			this.fixedRestPolicy.validate(be, predTime, flowRest.getFixedRestTimezone());
		}
	}

}
