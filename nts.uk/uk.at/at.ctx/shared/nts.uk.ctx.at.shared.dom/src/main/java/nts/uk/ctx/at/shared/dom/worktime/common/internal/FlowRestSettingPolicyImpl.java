/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.FlowRestSettingPolicy;
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
	public void validate(FlowRestSetting flowRestSetting, PredetemineTimeSetting predTime) {
		
		// Validate #Msg_781
		if (flowRestSetting.getFlowPassageTime().greaterThanOrEqualTo(predTime.getRangeTimeDay())
				|| flowRestSetting.getFlowRestTime().greaterThan(predTime.getRangeTimeDay())) {
			throw new BusinessException("Msg_781");
		}
	}

}
