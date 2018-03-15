/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset.internal;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.flowset.policy.FlowStampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class FlowStampReflectTimezonePolicyImpl.
 */
@Stateless
public class FlowStampReflectTimezonePolicyImpl implements FlowStampReflectTimezonePolicy {
	
	private static final Integer WORK_NO_1 = 1;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowStampReflectTimezonePolicy#
	 * validate(nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting,
			FlowStampReflectTimezone flowStampReflectTimezone) {
		this.validateStampReflectTimezone(be, predetemineTimeSetting, flowStampReflectTimezone);
	}

	/**
	 * Validate stamp reflect timezone.
	 *
	 * @param predetemineTimeSetting
	 *            the predetemine time setting
	 * @param flowStampReflectTimezone
	 *            the flow stamp reflect timezone
	 */
	private void validateStampReflectTimezone(BundledBusinessException be,
			PredetemineTimeSetting predetemineTimeSetting, FlowStampReflectTimezone flowStampReflectTimezone) {
		// Msg_516
		TimeWithDayAttr startTime = predetemineTimeSetting.getStartDateClock();
		TimeWithDayAttr endTime = startTime.forwardByMinutes(predetemineTimeSetting.getRangeTimeDay().valueAsMinutes());
		PrescribedTimezoneSetting timezone = predetemineTimeSetting.getPrescribedTimezoneSetting();
		flowStampReflectTimezone.getStampReflectTimezones().forEach(stampReflectTz -> {
			if (stampReflectTz.getStartTime().lessThan(startTime) || stampReflectTz.getEndTime().greaterThan(endTime)) {
				be.addMessage("Msg_516");
			}

			if (WORK_NO_1.equals(stampReflectTz.getWorkNo().v())) {
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
			}
		});

	}
}
