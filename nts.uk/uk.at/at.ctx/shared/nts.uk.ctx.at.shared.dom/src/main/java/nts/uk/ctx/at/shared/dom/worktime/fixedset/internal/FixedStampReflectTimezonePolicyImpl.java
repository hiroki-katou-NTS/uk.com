/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.FixedStampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class FixedStampReflectTimezonePolicyImpl.
 */
@Stateless
public class FixedStampReflectTimezonePolicyImpl implements FixedStampReflectTimezonePolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.
	 * FixedStampReflectTimezonePolicy#validate(nts.arc.error.
	 * BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting,
			FixedWorkSetting fixedWorkSetting) {
		this.validateStampReflectTimezone(be, predetemineTimeSetting, fixedWorkSetting);
	}

	/**
	 * Validate stamp reflect timezone.
	 *
	 * @param be
	 *            the be
	 * @param predetemineTimeSetting
	 *            the predetemine time setting
	 * @param fixedWorkSetting
	 *            the fixed work setting
	 */
	private void validateStampReflectTimezone(BundledBusinessException be,
			PredetemineTimeSetting predetemineTimeSetting, FixedWorkSetting fixedWorkSetting) {
		// Msg_516
		TimeWithDayAttr startTime = predetemineTimeSetting.getStartDateClock();
		TimeWithDayAttr endTime = startTime.forwardByMinutes(predetemineTimeSetting.getRangeTimeDay().valueAsMinutes());
		PrescribedTimezoneSetting timezone = predetemineTimeSetting.getPrescribedTimezoneSetting();
		fixedWorkSetting.getLstStampReflectTimezone().forEach(stampReflectTz -> {
			// Msg_516
			if (stampReflectTz.getStartTime().lessThan(startTime) || stampReflectTz.getEndTime().greaterThan(endTime)) {
				be.addMessage("Msg_516");
			}

			if (stampReflectTz.getWorkNo().v().equals(1)) {
				// Msg_1028
				if (GoLeavingWorkAtr.GO_WORK.equals(stampReflectTz.getClassification())
						&& stampReflectTz.getStartTime().greaterThan(timezone.getTimezoneShiftOne().getStart())) {
					be.addMessage("Msg_1028");
				}

				// Msg_1029
				if (GoLeavingWorkAtr.LEAVING_WORK.equals(stampReflectTz.getClassification())
						&& stampReflectTz.getEndTime().lessThan(timezone.getTimezoneShiftOne().getEnd())) {
					be.addMessage("Msg_1029");
				}

				// #Msg_1030
				if (GoLeavingWorkAtr.GO_WORK.equals(stampReflectTz.getClassification()) && timezone.isUseShiftTwo()
						&& (stampReflectTz.getEndTime()
								.greaterThanOrEqualTo(timezone.getTimezoneShiftTwo().getStart()))) {

				}

			}

			// #Msg_1031

			// #Msg_1032

			// #Msg_1033

			// #Msg_1034
		});

	}
}
