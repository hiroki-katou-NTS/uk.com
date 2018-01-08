/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRoundingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class EmTimeZoneSetPolicyImpl.
 */
@Stateless
public class EmTimeZoneSetPolicyImpl implements EmTimeZoneSetPolicy {

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
	public void validate(PredetemineTimeSetting predTime, EmTimeZoneSet etzSet) {
		val emTimezon = etzSet.getTimezone();
		val prescribed = predTime.getPrescribedTimezoneSetting();

		// validate msg_516
		if (this.tzrPolicy.validateRange(predTime, emTimezon)) {
			throw new BusinessException("Msg_516","KMK003_86");
		}

		// validate msg_773
		if (!prescribed.getTimezoneShiftTwo().isUsed()
				&& !emTimezon.isBetweenOrEqual(prescribed.getTimezoneShiftOne())) {
			throw new BusinessException("Msg_773","KMK003_86");
		}

		// validate msg_774
		if (prescribed.getTimezoneShiftTwo().isUsed() && (!emTimezon.isBetweenOrEqual(prescribed.getTimezoneShiftOne())
				&& !emTimezon.isBetweenOrEqual(prescribed.getTimezoneShiftTwo()))) {
			throw new BusinessException("Msg_774","KMK003_86");
		}
	}

}
