/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezonePolicy;
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

	/** The stamp reflect timezone policy. */
	@Inject
	private StampReflectTimezonePolicy stampReflectTimezonePolicy;
	
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
			if (stampReflectTz.isEmpty()) {
				return;
			}
			this.stampReflectTimezonePolicy.validate(be, false, stampReflectTz,predetemineTimeSetting);
			
			// Msg_516
			if (stampReflectTz.getStartTime().lessThan(startTime) || stampReflectTz.getEndTime().greaterThan(endTime)) {
				if (stampReflectTz.isGoWork1()) {
					be.addMessage("Msg_516", "#KMK003_271");
				} else if (stampReflectTz.isGoWork2()) {
					if (predetemineTimeSetting.getPrescribedTimezoneSetting().isUseShiftTwo()) {
						be.addMessage("Msg_516", "#KMK003_272");
					}					
				} else if (stampReflectTz.isLeaveWork1()) {
					be.addMessage("Msg_516", "#KMK003_274");
				} else if (stampReflectTz.isLeaveWork2()) {
					if (predetemineTimeSetting.getPrescribedTimezoneSetting().isUseShiftTwo()) {
						be.addMessage("Msg_516", "#KMK003_275");
					}	
				}
			}
		});

		if (opGoWork1Stamp.isPresent()) {
			//	「区分＝出勤&&勤務NO=1 の場合の 開始時刻」　　　　　　　　　　　　　
			//	開始時刻 ＞ 勤務NO=1の場合の所定時間帯設定.時間帯.開始
			//	#Msg_1028
			if (opGoWork1Stamp.get().getStartTime().greaterThan(timezone.getTimezoneShiftOne().getStart())) {
				be.addMessage("Msg_1028");
			}
			//	「区分＝出勤&&勤務NO=1 の場合の 終了時刻」　　　　　　　　　　　　　　　　　　
			//	所定時間帯設定.所定時間帯.時間帯.勤務No＝2 && 所定時間帯設定.所定時間帯.時間帯.使用区分 ＝ するの場合
			//	終了時刻 ＞＝ 勤務NO=2の場合の所定時間帯設定.時間帯.開始
			// #Msg_1030
			if (timezone.isUseShiftTwo() && (opGoWork1Stamp.get().getEndTime().greaterThanOrEqualTo(timezone.getTimezoneShiftTwo().getStart()))) {
				be.addMessage("Msg_1030");
			}
		}
		
		if (opLeavingWork1Stamp.isPresent()) {
			//	「区分＝退勤&&勤務NO=1 の場合の 終了時刻」　　　　　　　　　　　　　
			//	終了時刻 ＜ 勤務NO=1の場合の所定時間帯設定.時間帯.終了
			//	#Msg_1029
			if (opLeavingWork1Stamp.get().getEndTime().lessThan(timezone.getTimezoneShiftOne().getEnd())) {
				be.addMessage("Msg_1029");
			}
		}
		
		if (opGoWork2Stamp.isPresent()) {
			//	「区分＝出勤&&勤務NO=2 の場合の 開始時刻」
			//	所定時間帯設定.所定時間帯.時間帯.勤務No＝2 && 所定時間帯設定.所定時間帯.時間帯.使用区分 ＝ するの場合
			//	開始時刻 ＞ 勤務NO=2の場合の所定時間帯設定.時間帯.開始
			//	#Msg_1031
			if (timezone.isUseShiftTwo() && (opGoWork2Stamp.get().getStartTime().greaterThan(timezone.getTimezoneShiftTwo().getStart()))) {
				be.addMessage("Msg_1031");
			}
		}
		
		if (opLeavingWork2Stamp.isPresent()) {
			//	「区分＝退勤&&勤務NO=2 の場合の 終了時刻」　　　　　　　　　　　　　　　　　　　　
			//	所定時間帯設定.所定時間帯.時間帯.勤務No＝2 && 所定時間帯設定.所定時間帯.時間帯.使用区分 ＝ するの場合
			//	終了時刻 ＜ 勤務NO=2の場合の所定時間帯設定.時間帯.終了
			//	#Msg_1032
			if (timezone.isUseShiftTwo() && (opLeavingWork2Stamp.get().getEndTime().lessThan(timezone.getTimezoneShiftTwo().getEnd()))) {
				be.addMessage("Msg_1032");
			}
		}
		
		if (opGoWork1Stamp.isPresent() && opGoWork2Stamp.isPresent()) {
			//	「区分＝出勤&&勤務NO=1 の場合の 終了時刻、勤務NO=2 の場合の 開始時刻」　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
			//	所定時間帯設定.所定時間帯.時間帯.勤務No＝2 && 所定時間帯設定.所定時間帯.時間帯.使用区分 ＝ するの場合
			//	勤務NO=1 の場合の 終了時刻 ＞＝ 勤務NO=2 の場合の 開始時刻
			//	#Msg_1033
			if (timezone.isUseShiftTwo() && (opGoWork1Stamp.get().getEndTime().greaterThanOrEqualTo(opGoWork2Stamp.get().getStartTime()))) {
				be.addMessage("Msg_1033");
			}
		}
		
		if (opLeavingWork1Stamp.isPresent() && opLeavingWork2Stamp.isPresent()) {		
			//	「区分＝退勤&&勤務NO=1 の場合の 終了時刻、勤務NO=2 の場合の 開始時刻」　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
			//	所定時間帯設定.所定時間帯.時間帯.勤務No＝2 && 所定時間帯設定.所定時間帯.時間帯.使用区分 ＝ するの場合
			//	勤務NO=1 の場合の 終了時刻 ＞＝ 勤務NO=2 の場合の 開始時刻
			//	#Msg_1034
			if (timezone.isUseShiftTwo() && (opLeavingWork1Stamp.get().getEndTime().greaterThanOrEqualTo(opLeavingWork2Stamp.get().getStartTime()))) {
				be.addMessage("Msg_1034");
			}
		}
	}
}
