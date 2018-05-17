/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.FixedWorkTimezoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.FlexHalfDayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowWorkRestTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Class FlexHalfDayWorkTimePolicyImpl.
 */
@Stateless
public class FlexHalfDayWorkTimePolicyImpl implements FlexHalfDayWorkTimePolicy {

	/** The fixed wtz set policy. */
	@Inject
	private FixedWorkTimezoneSetPolicy fixedWtzSetPolicy;

	/** The flow rest policy. */
	@Inject
	private FlowWorkRestTimezonePolicy flowRestPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimePolicy#
	 * validate(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime, boolean)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimeDisplayMode displayMode,
			FlexHalfDayWorkTime halfDayWork, boolean isUseHalfDayShift) {
		this.fixedWtzSetPolicy.validateFlex(be, predTime, halfDayWork.getWorkTimezone(), displayMode.getDisplayMode(),
				halfDayWork.getAmpmAtr(), isUseHalfDayShift);

		// validate FlowWorkRestTimezone
		this.flowRestPolicy.validate(be, predTime, halfDayWork.getRestTimezone());
		
		if (!((AmPmAtr.AM.equals(halfDayWork.getAmpmAtr()) && DisplayMode.DETAIL.equals(displayMode) && !isUseHalfDayShift)
				|| (AmPmAtr.PM.equals(halfDayWork.getAmpmAtr()) && DisplayMode.DETAIL.equals(displayMode) && !isUseHalfDayShift)
				|| ((AmPmAtr.AM.equals(halfDayWork.getAmpmAtr()) || AmPmAtr.PM.equals(halfDayWork.getAmpmAtr())) && DisplayMode.SIMPLE.equals(displayMode)))) {
			
			// Msg_755
			if (halfDayWork.getRestTimezone().isFixRestTime() && !halfDayWork.isInFixedWork(halfDayWork.getRestTimezone())) {
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
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.policy.
	 * FlexHalfDayWorkTimePolicy#filterTimezone(nts.uk.ctx.at.shared.dom.
	 * worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode, boolean)
	 */
	@Override
	public void filterTimezone(PredetemineTimeSetting predTime, FlexHalfDayWorkTime origin, DisplayMode displayMode,
			boolean useHalfDayShift) {
		this.fixedWtzSetPolicy.filterTimezone(predTime, origin.getWorkTimezone(), displayMode, origin.getAmpmAtr(),
				useHalfDayShift);
	}

}
