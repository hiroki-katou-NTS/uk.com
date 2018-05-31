/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowRestSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlowRestSettingPolicyImpl.
 */
@Stateless
public class FlowRestSettingPolicyImpl implements FlowRestSettingPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingPolicy#validate(
	 * nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlowRestSetting flowRestSetting) {

		// Validate #Msg_781
		if (flowRestSetting.getFlowPassageTime().greaterThanOrEqualTo(predTime.getRangeTimeDay())
				|| flowRestSetting.getFlowRestTime().greaterThan(predTime.getRangeTimeDay())) {
			be.addMessage("Msg_781");
		}
	}

}
