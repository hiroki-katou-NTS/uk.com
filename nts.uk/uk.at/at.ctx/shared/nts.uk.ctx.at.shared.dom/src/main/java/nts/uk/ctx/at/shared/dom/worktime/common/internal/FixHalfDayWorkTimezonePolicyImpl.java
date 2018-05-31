/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.FixedWorkTimezoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Class FixHalfDayWorkTimezonePolicyImpl.
 */
@Stateless
public class FixHalfDayWorkTimezonePolicyImpl implements FixHalfDayWorkTimezonePolicy {

	/** The fixed wtz policy. */
	@Inject
	private FixedWorkTimezoneSetPolicy fixedWtzPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.
	 * FixHalfDayWorkTimezonePolicy#validateFixedAndDiff(nts.arc.error.
	 * BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone,
	 * boolean)
	 */
	@Override
	public void validateFixedAndDiff(BundledBusinessException be, PredetemineTimeSetting predTime,
			WorkTimeDisplayMode displayMode, FixHalfDayWorkTimezone halfDayWork, boolean isUseHalfDayShift) {
		this.fixedWtzPolicy.validateFixedAndDiff(be, predTime, halfDayWork.getWorkTimezone(),
				displayMode.getDisplayMode(), halfDayWork.getDayAtr(), isUseHalfDayShift);
				
		if (!((AmPmAtr.AM.equals(halfDayWork.getDayAtr()) && DisplayMode.DETAIL.equals(displayMode) && !isUseHalfDayShift)
				|| (AmPmAtr.PM.equals(halfDayWork.getDayAtr()) && DisplayMode.DETAIL.equals(displayMode) && !isUseHalfDayShift)
				|| ((AmPmAtr.AM.equals(halfDayWork.getDayAtr()) || AmPmAtr.PM.equals(halfDayWork.getDayAtr())) && DisplayMode.SIMPLE.equals(displayMode)))) {
			
			// Msg_755
			if (!halfDayWork.isInFixedWork()) {
				be.addMessage("Msg_755");
			}
			
			// Msg_845
			if (halfDayWork.getWorkTimezone().isOverTimeAndEmTimeOverlap()) {
				be.addMessage("Msg_845", "KMK003_89");
			}
		}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.
	 * FixHalfDayWorkTimezonePolicy#validateFlex(nts.arc.error.
	 * BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone,
	 * boolean)
	 */
	@Override
	public void validateFlex(BundledBusinessException be, PredetemineTimeSetting predTime,
			WorkTimeDisplayMode displayMode, FixHalfDayWorkTimezone halfDayWork, boolean isUseHalfDayShift) {
		this.fixedWtzPolicy.validateFlex(be, predTime, halfDayWork.getWorkTimezone(), displayMode.getDisplayMode(),
				halfDayWork.getDayAtr(), isUseHalfDayShift);
		
		if (!((AmPmAtr.AM.equals(halfDayWork.getDayAtr()) && DisplayMode.DETAIL.equals(displayMode) && !isUseHalfDayShift)
				|| (AmPmAtr.PM.equals(halfDayWork.getDayAtr()) && DisplayMode.DETAIL.equals(displayMode) && !isUseHalfDayShift)
				|| ((AmPmAtr.AM.equals(halfDayWork.getDayAtr()) || AmPmAtr.PM.equals(halfDayWork.getDayAtr())) && DisplayMode.SIMPLE.equals(displayMode)))) {
			
			// Msg_755
			if (!halfDayWork.isInFixedWork()) {
				be.addMessage("Msg_755");
			}
			
			// Msg_845
			if (halfDayWork.getWorkTimezone().isOverTimeAndEmTimeOverlap()) {
				be.addMessage("Msg_845", "KMK003_89");
			}
		}	
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.FixHalfDayWorkTimezonePolicy#
	 * filterTimezone(nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode, boolean)
	 */
	@Override
	public void filterTimezone(PredetemineTimeSetting predTime, FixHalfDayWorkTimezone origin, DisplayMode displayMode,
			boolean useHalfDayShift) {
		this.fixedWtzPolicy.filterTimezone(predTime, origin.getWorkTimezone(), displayMode, origin.getDayAtr(),
				useHalfDayShift);
	}
}
