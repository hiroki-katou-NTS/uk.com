/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlowWorkRestTimezonePolicyImpl.
 */
@Stateless
public class FlowWorkRestTimezonePolicyImpl implements FlowWorkRestTimezonePolicy {

	/** The fixed rest policy. */
	@Inject
	private TimezoneOfFixedRestTimeSetPolicy fixedRestPolicy;

	/** The flow rest setting policy. */
	@Inject
	private FlowRestSettingPolicy flowRestSettingPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezonePolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone)
	 */
	@Override
	public void validate(PredetemineTimeSetting predTime, FlowWorkRestTimezone flowRest) {
		// validate TimezoneOfFixedRestTimeSet
		this.fixedRestPolicy.validate(predTime, flowRest.getFixedRestTimezone());
		

		// validate list flow 
		this.flowRestSettingPolicy.validate(flowRest.getFlowRestTimezone().getHereAfterRestSet(), predTime);
		
		//
		flowRest.getFlowRestTimezone().getFlowRestSets().stream().forEach(item -> {
			this.flowRestSettingPolicy.validate(item, predTime);
		});
	}

}
