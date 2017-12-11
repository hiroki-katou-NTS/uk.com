/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlWorkSettingPolicyImpl.
 */
@Stateless
public class FlWorkSettingPolicyImpl implements FlWorkSettingPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingPolicy#validate(
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(FlWorkSetting flowSet, PredetemineTimeSetting predSet) {
		// TODO Auto-generated method stub

	}

}
