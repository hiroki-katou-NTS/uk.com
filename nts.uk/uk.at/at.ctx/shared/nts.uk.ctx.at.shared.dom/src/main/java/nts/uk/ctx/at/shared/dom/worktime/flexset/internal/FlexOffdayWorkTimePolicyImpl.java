/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.FlexOffdayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;

/**
 * The Class FlexOffdayWorkTimePolicyImpl.
 */
@Stateless
public class FlexOffdayWorkTimePolicyImpl implements FlexOffdayWorkTimePolicy {
	
	/** The predetemine policy service. */
	@Inject
	private PredeteminePolicyService predeteminePolicyService;

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
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, FlexOffdayWorkTime flexOffDay) {
		// validate FlowWorkRestTimezone
		this.flowRestPolicy.validate(be, predTime, flexOffDay.getRestTimezone());
		
		flexOffDay.getLstWorkTimezone().forEach(workTimezone -> {
			if (this.predeteminePolicyService.validateOneDay(predTime, workTimezone.getTimezone().getStart(),
					workTimezone.getTimezone().getEnd())) {
				be.addMessage("Msg_516", "KMK003_90");
			}
		});
	}

}
