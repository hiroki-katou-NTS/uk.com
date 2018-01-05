/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class WorkTimezoneCommonSetPolicyImpl.
 */
@Stateless
public class WorkTimezoneCommonSetPolicyImpl implements WorkTimezoneCommonSetPolicy {

	/** The wtz other policy. */
	@Inject
	private WorkTimezoneOtherSubHolTimeSetPolicy wtzOtherPolicy;

	/** The wtz late early policy. */
	@Inject
	private WorkTimezoneLateEarlySetPolicy wtzLateEarlyPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet)
	 */
	@Override
	public void validate(PredetemineTimeSetting predSet, WorkTimezoneCommonSet wtzCommon) {
		// validate WorkTimezoneOtherSubHolTimeSet
		wtzCommon.getSubHolTimeSet().forEach(subHolTimeSet -> {
			this.wtzOtherPolicy.validate(predSet, subHolTimeSet);
		});		

		// validate WorkTimezoneLateEarlySet
		this.wtzLateEarlyPolicy.validate(predSet, wtzCommon.getLateEarlySet());
	}

}
