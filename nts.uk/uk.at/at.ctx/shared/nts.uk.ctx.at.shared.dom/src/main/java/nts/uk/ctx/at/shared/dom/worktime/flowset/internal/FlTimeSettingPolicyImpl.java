/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlTimeSettingPolicyImpl.
 */
@Stateless
public class FlTimeSettingPolicyImpl implements FlTimeSettingPolicy {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeSettingPolicy#validate(nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting, nts.uk.ctx.at.shared.dom.worktime.flowset.FlTimeSetting)
	 */
	@Override
	public void validate(PredetemineTimeSetting predTime, FlowTimeSetting flTimeSetting) {
		
		// Validate #Msg_718
		if (flTimeSetting.getElapsedTime().greaterThanOrEqualTo(predTime.getRangeTimeDay())) {
			throw new BusinessException("Msg_718");
		}
	}

}
