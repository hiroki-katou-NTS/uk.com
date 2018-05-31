/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkHolidayTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlowWorkHolidayTimezonePolicyImpl.
 */
@Stateless
public class FlowWorkHolidayTimezonePolicyImpl implements FlowWorkHolidayTimezonePolicy {

	/** The flow time setting policy. */
	@Inject
	private FlowTimeSettingPolicy flowTimeSettingPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimezonePolicy#
	 * validate(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkHolidayTimeZone)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime,
			FlowWorkHolidayTimeZone flowHoliday) {
		this.flowTimeSettingPolicy.validate(be, predTime, flowHoliday.getFlowTimeSetting());
	}

}
