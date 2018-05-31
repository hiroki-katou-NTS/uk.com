/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowRestSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlowRestTimezonePolicyImpl.
 */
@Stateless
public class FlowRestTimezonePolicyImpl implements FlowRestTimezonePolicy {

	/** The flow rest setting policy. */
	@Inject
	private FlowRestSettingPolicy flowRestSettingPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezonePolicy#validate
	 * (nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowRestTimezone flowRestTimezone) {
		// validate FlowRestSets
		flowRestTimezone.getFlowRestSets().forEach(flowRestSet -> {
			this.flowRestSettingPolicy.validate(be, predTime, flowRestSet);
		});
		
		//validate HereAfterRestSet
		if (flowRestTimezone.isUseHereAfterRestSet()) {
			this.flowRestSettingPolicy.validate(be, predTime, flowRestTimezone.getHereAfterRestSet());
		}
	}

}
