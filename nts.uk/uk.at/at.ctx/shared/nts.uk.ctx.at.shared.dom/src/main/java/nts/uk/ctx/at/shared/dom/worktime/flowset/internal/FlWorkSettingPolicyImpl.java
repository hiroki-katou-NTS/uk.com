/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlHalfDayWtzPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlOffdayWtzPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlWorkSettingPolicyImpl.
 */
@Stateless
public class FlWorkSettingPolicyImpl implements FlWorkSettingPolicy {

	/** The flow half policy. */
	@Inject
	private FlHalfDayWtzPolicy flowHalfPolicy;

	/** The flow off policy. */
	@Inject
	private FlOffdayWtzPolicy flowOffPolicy;

	/** The wtz common set policy. */
	@Inject
	private WorkTimezoneCommonSetPolicy wtzCommonSetPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSettingPolicy#validate(
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(FlowWorkSetting flowSet, PredetemineTimeSetting predSet) {
		// validate FlHalfDayWtz
		this.flowHalfPolicy.validate(predSet, flowSet.getHalfDayWorkTimezone());

		// validate FlOffdayWtz
		this.flowOffPolicy.validate(predSet, flowSet.getOffdayWorkTimezone());

		// validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(predSet, flowSet.getCommonSetting());
	}

}
