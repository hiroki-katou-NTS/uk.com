/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class DiffTimeHalfDayWorkTimezonePolicyImpl.
 */
@Stateless
public class DiffTimeHalfDayWorkTimezonePolicyImpl implements DiffTimeHalfDayWorkTimezonePolicy {

	/** The diff timezone policy. */
	@Inject
	private DiffTimezoneSettingPolicy diffTimezonePolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeHalfDayWorkTimezonePolicy#validate(nts.uk.ctx.at.shared.dom.
	 * worktime.difftimeset.DiffTimeHalfDayWorkTimezone,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, DiffTimeHalfDayWorkTimezone diffTimeHalfDay, PredetemineTimeSetting predSet) {
		this.diffTimezonePolicy.validate(be, diffTimeHalfDay.getWorkTimezone(), predSet);
	}

}
