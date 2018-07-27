/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlowTimeSettingPolicyImpl.
 */
@Stateless
public class FlowTimeSettingPolicyImpl implements FlowTimeSettingPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSettingPolicy#validate(
	 * nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting, FlowTimeSetting flowTimeSetting) {
		this.validateElapsedTime(be, predetemineTimeSetting, flowTimeSetting);
	}

	/**
	 * Validate elapsed time.
	 *
	 * @param predetemineTimeSetting the predetemine time setting
	 * @param flowTimeSetting the flow time setting
	 */
	private void validateElapsedTime(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting, FlowTimeSetting flowTimeSetting) {
		// Msg_781
		if (flowTimeSetting.getElapsedTime().greaterThan(predetemineTimeSetting.getRangeTimeDay())) {
			be.addMessage("Msg_781");
		}
	}

}
