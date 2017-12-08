/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class OverTimeOfTimeZoneSetPolicyImpl.
 */
@Stateless
public class OverTimeOfTimeZoneSetPolicyImpl implements OverTimeOfTimeZoneSetPolicy {

	/** The tzr policy. */
	@Inject
	private TimeZoneRoundingPolicy tzrPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy#validate(nts
	 * .uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet)
	 */
	@Override
	public void validate(PredetemineTimeSetting predTime, OverTimeOfTimeZoneSet otSet) {
		// validate msg_516
		this.tzrPolicy.validateRange(predTime, otSet.getTimezone());

		// validate msg_519
		val otTimezone = otSet.getTimezone();
		val shift1Timezone = predTime.getPrescribedTimezoneSetting().getTimezoneShiftOne();
		val shift2Timezone = predTime.getPrescribedTimezoneSetting().getTimezoneShiftTwo();
		// TODO: trong ea chua mo ta truong hop khong co shift timezone
		if (otTimezone.isBetweenOrEqual(shift1Timezone) || otTimezone.isBetweenOrEqual(shift2Timezone)) {
			throw new BusinessException("Msg_519");
		}
	}
}
