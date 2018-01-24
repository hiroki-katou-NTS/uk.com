/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkTimezoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class FlexHalfDayWorkTimePolicyImpl.
 */
@Stateless
public class FlexHalfDayWorkTimePolicyImpl implements FlexHalfDayWorkTimePolicy {

	/** The fixed wtz set policy. */
	@Inject
	private FixedWorkTimezoneSetPolicy fixedWtzSetPolicy;

	@Inject
	private FlowWorkRestTimezonePolicy flowRestPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimePolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(FlexHalfDayWorkTime flexHalfDay, PredetemineTimeSetting predTime) {
		// validate FixedWorkTimezoneSet
		this.fixedWtzSetPolicy.validate(flexHalfDay.getWorkTimezone(), predTime);
		// validate FlowWorkRestTimezone
		this.flowRestPolicy.validate(predTime, flexHalfDay.getRestTimezone());
	}

}
