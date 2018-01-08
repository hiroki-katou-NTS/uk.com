/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;

/**
 * The Class TimeZoneRoundingPolicyImpl.
 */
@Stateless
public class TimeZoneRoundingPolicyImpl implements TimeZoneRoundingPolicy {

	/** The pred service. */
	@Inject
	private PredeteminePolicyService predService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingPolicy#
	 * validateRange()
	 */
	@Override
	public boolean validateRange(PredetemineTimeSetting predTime, TimeZoneRounding tzRounding) {
		return this.predService.validateOneDay(predTime, tzRounding.getStart(), tzRounding.getEnd());
	}

}
