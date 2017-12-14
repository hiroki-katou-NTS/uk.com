/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlexOffdayWorkTimePolicyImpl.
 */
@Stateless
public class FlexOffdayWorkTimePolicyImpl implements FlexOffdayWorkTimePolicy {

	/** The flow rest policy. */
	@Inject
	private FlowWorkRestTimezonePolicy flowRestPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimePolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTime)
	 */
	@Override
	public void validate(PredetemineTimeSetting predTime, FlexOffdayWorkTime flexOffDay) {
		// validate FlowWorkRestTimezone
		this.flowRestPolicy.validate(predTime, flexOffDay.getRestTimezone());
	}

}
