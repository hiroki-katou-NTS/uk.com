/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimeHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimezoneSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

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
	 * DiffTimeHalfDayWorkTimezonePolicy#validate(nts.arc.error.
	 * BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeHalfDayWorkTimezone, boolean)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimeDisplayMode displayMode,
			DiffTimeHalfDayWorkTimezone halfDayWork, boolean isUseHalfDayShift) {
		this.diffTimezonePolicy.validate(be, predTime, halfDayWork.getWorkTimezone(), displayMode.getDisplayMode(),
				halfDayWork.getAmPmAtr(), isUseHalfDayShift);
			
		if (!((AmPmAtr.AM.equals(halfDayWork.getAmPmAtr()) && DisplayMode.DETAIL.equals(displayMode) && !isUseHalfDayShift)
				|| (AmPmAtr.PM.equals(halfDayWork.getAmPmAtr()) && DisplayMode.DETAIL.equals(displayMode) && !isUseHalfDayShift)
				|| ((AmPmAtr.AM.equals(halfDayWork.getAmPmAtr()) || AmPmAtr.PM.equals(halfDayWork.getAmPmAtr())) && DisplayMode.SIMPLE.equals(displayMode)))) {
			
			// Msg_755
			if (halfDayWork.restInWork()) {
				be.addMessage("Msg_755");
			}
			
			// Msg_845
			if (halfDayWork.getWorkTimezone().isOverTimeAndEmTimeOverlap()) {
				be.addMessage("Msg_845", "KMK003_89");
			}
		}		
	}

	/**
	 * Filter timezone.
	 *
	 * @param predTime
	 *            the pred time
	 * @param origin
	 *            the origin
	 * @param target
	 *            the target
	 * @param displayMode
	 *            the display mode
	 * @param useHalfDayShift
	 *            the use half day shift
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.
	 * DiffTimeHalfDayWorkTimezonePolicy#filterTimezone(nts.uk.ctx.at.shared.dom
	 * .worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeHalfDayWorkTimezone,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeHalfDayWorkTimezone,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode, boolean)
	 */
	@Override
	public void filterTimezone(PredetemineTimeSetting predTime, DiffTimeHalfDayWorkTimezone origin,
			DisplayMode displayMode, boolean useHalfDayShift) {
		this.diffTimezonePolicy.filterTimezone(predTime, origin.getWorkTimezone(), displayMode, origin.getAmPmAtr(),
				useHalfDayShift);
	}

}
