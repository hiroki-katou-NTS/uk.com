/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
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

	private static final Integer WORK_NO_1 = 1;

	private static final Integer WORK_NO_2 = 2;

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

		Optional<StampReflectTimezone> opGoWork1Stamp = fixedWorkSetting.getLstStampReflectTimezone().stream()
				.filter(stampReflectTz -> (WORK_NO_1.equals(stampReflectTz.getWorkNo().v()) && (GoLeavingWorkAtr.GO_WORK.equals(stampReflectTz.getClassification()))))
				.findFirst();
		Optional<StampReflectTimezone> opGoWork2Stamp = fixedWorkSetting.getLstStampReflectTimezone().stream()
				.filter(stampReflectTz -> (WORK_NO_2.equals(stampReflectTz.getWorkNo().v()) && (GoLeavingWorkAtr.GO_WORK.equals(stampReflectTz.getClassification()))))
				.findFirst();
		Optional<StampReflectTimezone> opLeavingWork1Stamp = fixedWorkSetting.getLstStampReflectTimezone().stream()
				.filter(stampReflectTz -> (WORK_NO_1.equals(stampReflectTz.getWorkNo().v()) && (GoLeavingWorkAtr.LEAVING_WORK.equals(stampReflectTz.getClassification()))))
				.findFirst();
		Optional<StampReflectTimezone> opLeavingWork2Stamp = fixedWorkSetting.getLstStampReflectTimezone().stream()
				.filter(stampReflectTz -> (WORK_NO_2.equals(stampReflectTz.getWorkNo().v()) && (GoLeavingWorkAtr.LEAVING_WORK.equals(stampReflectTz.getClassification()))))
				.findFirst();

		fixedWorkSetting.getLstStampReflectTimezone().forEach(stampReflectTz -> {
			// Msg_516
			if (stampReflectTz.getStartTime().lessThan(startTime) || stampReflectTz.getEndTime().greaterThan(endTime)) {
				be.addMessage("Msg_516");
			}
		});

		if (opGoWork1Stamp.isPresent()) {
			// Msg_1028
			if (opGoWork1Stamp.get().getStartTime().greaterThan(timezone.getTimezoneShiftOne().getStart())) {
				be.addMessage("Msg_1028");
			}
			// #Msg_1030
			if (timezone.isUseShiftTwo() && (opGoWork1Stamp.get().getEndTime().greaterThanOrEqualTo(timezone.getTimezoneShiftTwo().getStart()))) {
				be.addMessage("Msg_1030");
			}
		}
		
		if (opLeavingWork1Stamp.isPresent()) {
			// Msg_1029
			if (opLeavingWork1Stamp.get().getEndTime().lessThan(timezone.getTimezoneShiftOne().getEnd())) {
				be.addMessage("Msg_1029");
			}
		}
		
		if (opGoWork2Stamp.isPresent()) {
			// #Msg_1031
			if (timezone.isUseShiftTwo() && (opGoWork2Stamp.get().getStartTime().greaterThan(timezone.getTimezoneShiftTwo().getStart()))) {
				be.addMessage("Msg_1031");
			}
		}
		
		if (opLeavingWork2Stamp.isPresent()) {
			// #Msg_1032
			if (timezone.isUseShiftTwo() && (opLeavingWork2Stamp.get().getEndTime().lessThan(timezone.getTimezoneShiftTwo().getEnd()))) {
				be.addMessage("Msg_1032");
			}
		}
		
		if (opGoWork1Stamp.isPresent() && opGoWork2Stamp.isPresent()) {
			// #Msg_1033
			if (timezone.isUseShiftTwo() && (opGoWork1Stamp.get().getEndTime().greaterThanOrEqualTo(opGoWork2Stamp.get().getStartTime()))) {
				be.addMessage("Msg_1033");
			}
		}
		
		if (opLeavingWork1Stamp.isPresent() && opLeavingWork2Stamp.isPresent()) {		
			// #Msg_1034
			if (timezone.isUseShiftTwo() && (opLeavingWork1Stamp.get().getEndTime().greaterThanOrEqualTo(opLeavingWork2Stamp.get().getStartTime()))) {
				be.addMessage("Msg_1034");
			}
		}
	}
}
