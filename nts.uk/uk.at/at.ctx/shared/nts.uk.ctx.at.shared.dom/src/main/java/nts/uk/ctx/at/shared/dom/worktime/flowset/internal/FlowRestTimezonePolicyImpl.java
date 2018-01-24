/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestTimezonePolicy;
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
	public void validate(PredetemineTimeSetting predTime, FlowRestTimezone flowRestTimezone) {
		flowRestTimezone.getFlowRestSets().forEach(flowRestSet -> {
			this.flowRestSettingPolicy.validate(predTime, flowRestSet);
		});
	}

}
