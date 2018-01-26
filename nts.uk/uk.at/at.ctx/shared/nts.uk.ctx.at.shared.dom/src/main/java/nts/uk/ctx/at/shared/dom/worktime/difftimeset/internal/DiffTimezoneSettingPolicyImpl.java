/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class DiffTimezoneSettingPolicyImpl.
 */
@Stateless
public class DiffTimezoneSettingPolicyImpl implements DiffTimezoneSettingPolicy {

	/** The em tz policy. */
	@Inject
	private EmTimeZoneSetPolicy emTzPolicy;

	/** The ot set policy. */
	@Inject
	private OverTimeOfTimeZoneSetPolicy otSetPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingPolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimezoneSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, DiffTimezoneSetting diffTzSet, PredetemineTimeSetting predSet) {
		// validate list emTimezone
		diffTzSet.getEmploymentTimezones().forEach(empTz -> {
			this.emTzPolicy.validate(be, predSet, empTz);
			this.emTzPolicy.validateTimezone(be, predSet.getPrescribedTimezoneSetting(), empTz.getTimezone());
		});

		// validate list DiffTimeOTTimezoneSet
		diffTzSet.getOTTimezones().forEach(otTimezone -> this.otSetPolicy.validate(be, predSet, otTimezone));
	}

}
