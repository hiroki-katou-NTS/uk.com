/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimeStampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DiffTimeStampReflectTimezonePolicyImpl.
 */
@Stateless
public class DiffTimeStampReflectTimezonePolicyImpl implements DiffTimeStampReflectTimezonePolicy {

	/** The stamp reflect timezone policy. */
	@Inject
	private StampReflectTimezonePolicy stampReflectTimezonePolicy;

	/** The Constant WORK_NO_1. */
	private static final Integer WORK_NO_1 = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.
	 * DiffTimeStampReflectTimezonePolicy#validate(nts.arc.error.
	 * BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSetting,
			DiffTimeWorkSetting diffTimeWorkSetting) {
		this.validateStampReflectTimezone(be, predetemineTimeSetting, diffTimeWorkSetting);
	}

	/**
	 * Validate stamp reflect timezone.
	 *
	 * @param be
	 *            the be
	 * @param predetemineTimeSetting
	 *            the predetemine time setting
	 * @param diffTimeWorkSetting
	 *            the diff time work setting
	 */
	private void validateStampReflectTimezone(BundledBusinessException be,
			PredetemineTimeSetting predetemineTimeSetting, DiffTimeWorkSetting diffTimeWorkSetting) {
		// Msg_516
		TimeWithDayAttr startTime = predetemineTimeSetting.getStartDateClock();
		TimeWithDayAttr endTime = startTime.forwardByMinutes(predetemineTimeSetting.getRangeTimeDay().valueAsMinutes());
		PrescribedTimezoneSetting timezone = predetemineTimeSetting.getPrescribedTimezoneSetting();
		diffTimeWorkSetting.getStampReflectTimezone().getStampReflectTimezone().forEach(stampReflectTz -> {
			if (stampReflectTz.isEmpty()) {
				return;
			}
			this.stampReflectTimezonePolicy.validate(be, false, stampReflectTz,predetemineTimeSetting);

			if (stampReflectTz.getStartTime().lessThan(startTime) || stampReflectTz.getEndTime().greaterThan(endTime)) {
				if (stampReflectTz.isGoWork1()) {
					be.addMessage("Msg_516", "#KMK003_271");
				} else if (stampReflectTz.isLeaveWork1()) {
					be.addMessage("Msg_516", "#KMK003_274");
				}
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
