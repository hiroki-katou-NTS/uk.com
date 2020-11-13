/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * フレックス勤務設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.フレックス勤務設定.フレックス勤務設定
 */
@Getter
@NoArgsConstructor
public class FlexWorkSetting extends WorkTimeAggregateRoot implements Cloneable, WorkSetting {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The work time code. */
	// 就業時間帯コード
	private WorkTimeCode workTimeCode;

	/** The core time setting. */
	// コアタイム時間帯
	private CoreTimeSetting coreTimeSetting;

	/** The rest setting. */
	// 休憩設定
	private FlowWorkRestSetting restSetting;

	/** The offday work time. */
	// 休日勤務時間帯
	private FlexOffdayWorkTime offdayWorkTime;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The use half day shift. */
	// 半日用シフトを使用する
	private boolean useHalfDayShift;

	/** The lst half day work timezone. */
	// 平日勤務時間帯
	private List<FlexHalfDayWorkTime> lstHalfDayWorkTimezone;

	/** The lst stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> lstStampReflectTimezone;

	/** The calculate setting. */
	// 計算設定
	private FlexCalcSetting calculateSetting;

	/**
	 * Instantiates a new flex work setting.
	 *
	 * @param memento the memento
	 */
	public FlexWorkSetting(FlexWorkSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workTimeCode = memento.getWorkTimeCode();
		this.coreTimeSetting = memento.getCoreTimeSetting();
		this.restSetting = memento.getRestSetting();
		this.offdayWorkTime = memento.getOffdayWorkTime();
		this.commonSetting = memento.getCommonSetting();
		this.useHalfDayShift = memento.getUseHalfDayShift();
		this.lstHalfDayWorkTimezone = memento.getLstHalfDayWorkTimezone();
		this.lstStampReflectTimezone = memento.getLstStampReflectTimezone();
		this.calculateSetting = memento.getCalculateSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FlexWorkSettingSetMemento memento) {
		memento.setcompanyId(this.companyId);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setCoreTimeSetting(this.coreTimeSetting);
		memento.setRestSetting(this.restSetting);
		memento.setOffdayWorkTime(this.offdayWorkTime);
		memento.setCommonSetting(this.commonSetting);
		memento.setUseHalfDayShift(this.useHalfDayShift);
		memento.setLstHalfDayWorkTimezone(this.lstHalfDayWorkTimezone);
		memento.setLstStampReflectTimezone(this.lstStampReflectTimezone);
		memento.setCalculateSetting(this.calculateSetting);
	}

	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param workTimeType the work time type
	 * @param other the other
	 */
	public void correctData(ScreenMode screenMode, WorkTimeDivision workTimeType, FlexWorkSetting other) {
		// Dialog J: list stamp timezone
		Map<Entry<WorkNo, GoLeavingWorkAtr>, StampReflectTimezone> mapStampReflectTimezone = other
				.getLstStampReflectTimezone().stream()
				.collect(Collectors.toMap(
						item -> new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()),
						Function.identity()));
		this.lstStampReflectTimezone.forEach(item -> item.correctData(screenMode, mapStampReflectTimezone
				.get(new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()))));

		this.commonSetting.correctData(screenMode, other.getCommonSetting());

		this.offdayWorkTime.correctData(screenMode, other);

		this.coreTimeSetting.correctData(screenMode, other.getCoreTimeSetting());
		// for dialog H
		this.restSetting.correctData(screenMode, other.getRestSetting(),
				this.getLstHalfDayWorkTimezone().size() > 0
						? this.getLstHalfDayWorkTimezone().get(0).getRestTimezone().isFixRestTime()
						: false);
	}

	/**
	 * Restore default data.
	 *
	 * @param screenMode the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		// Dialog J: list stamp timezone
		this.lstStampReflectTimezone.forEach(item -> item.correctDefaultData(screenMode));

		this.commonSetting.correctDefaultData(screenMode);

		this.coreTimeSetting.correctDefaultData(screenMode);

		// for dialog H
		this.restSetting.correctDefaultData(screenMode,
				this.getLstHalfDayWorkTimezone().size() > 0
						? this.getLstHalfDayWorkTimezone().get(0).getRestTimezone().isFixRestTime()
						: false);
	}

	/**
	 * create this Instance
	 *
	 * @return new Instance
	 */
	@Override
	public FlexWorkSetting clone() {
		FlexWorkSetting cloned = new FlexWorkSetting();
		try {
			cloned.companyId = this.companyId;
			cloned.workTimeCode = new WorkTimeCode(this.workTimeCode.v());
			cloned.coreTimeSetting = this.coreTimeSetting.clone();
			cloned.restSetting = this.restSetting.clone();
			cloned.offdayWorkTime = this.offdayWorkTime.clone();
			cloned.commonSetting = this.commonSetting.clone();
			cloned.useHalfDayShift = this.useHalfDayShift ? true : false;
			cloned.lstHalfDayWorkTimezone = this.lstHalfDayWorkTimezone.stream().map(c -> c.clone())
					.collect(Collectors.toList());
			cloned.lstStampReflectTimezone = this.lstStampReflectTimezone.stream().map(c -> c.clone())
					.collect(Collectors.toList());
			cloned.calculateSetting = this.calculateSetting.clone();
		} catch (Exception e) {
			throw new RuntimeException("AggregateTotalTimeSpentAtWork clone error.");
		}
		return cloned;
	}

	public List<EmTimeZoneSet> getEmTimeZoneSet(WorkType workType) {
		return getFlexHalfDayWorkTime(workType.getAttendanceHolidayAttr()).isPresent()
				? getFlexHalfDayWorkTime(workType.getAttendanceHolidayAttr()).get().getWorkTimezone()
						.getLstWorkingTimezone()
				: Collections.emptyList();
	}

	public List<OverTimeOfTimeZoneSet> getOverTimeOfTimeZoneSet(WorkType workType) {
		return getFlexHalfDayWorkTime(workType.getAttendanceHolidayAttr()).isPresent()
				? getFlexHalfDayWorkTime(workType.getAttendanceHolidayAttr()).get().getWorkTimezone().getLstOTTimezone()
				: Collections.emptyList();
	}

	private Optional<FlexHalfDayWorkTime> getFlexHalfDayWorkTime(AttendanceHolidayAttr attendanceHolidayAttr) {
		switch (attendanceHolidayAttr) {
		case FULL_TIME:
			return getFlexHalfDayWorkTime(AmPmAtr.ONE_DAY);
		case MORNING:
			return getFlexHalfDayWorkTime(AmPmAtr.AM);
		case AFTERNOON:
			return getFlexHalfDayWorkTime(AmPmAtr.PM);
		case HOLIDAY:
			return Optional.empty();
		default:
			throw new RuntimeException("Unknown AttendanceHolidayAttr");
		}
	}

	private Optional<FlexHalfDayWorkTime> getFlexHalfDayWorkTime(AmPmAtr amPmAtr) {
		return lstHalfDayWorkTimezone.stream().filter(timeZone -> timeZone.getAmpmAtr().equals(amPmAtr)).findFirst();
	}

	public FlowWorkRestTimezone getFlowWorkRestTimezone(WorkType workType) {
		if (workType.getDailyWork().isHolidayWork()) {
			return this.offdayWorkTime.getRestTimezone();
		}
		if (this.getFlexHalfDayWorkTime(workType.getAttendanceHolidayAttr()).isPresent()) {
			return this.getFlexHalfDayWorkTime(workType.getAttendanceHolidayAttr()).get().getRestTimezone();
		}
		throw new RuntimeException("Not get FlexHalfDayWorkTime");
	}

	/**
	 * 変更可能な勤務時間帯を取得する
	 * @param require Require
	 * @return 変更可能な時間帯
	 */
	@Override
	public ChangeableWorkingTimeZone getChangeableWorkingTimeZone(WorkSetting.Require require) {
		/**
		 * $就業の時間帯 = @平日勤務時間帯 : filter $.午前午後区分 == 1日 // 就業時間帯は "1日" しかないので。 map
		 * $.勤務時間帯.就業時間の時間帯 ()
		 *
		 */
		List<TimeSpanForCalc> lstWorkingTimezone = this.lstHalfDayWorkTimezone.stream()
				.filter(c -> c.getAmpmAtr() == AmPmAtr.ONE_DAY)
				.map(c -> {
					return c.getWorkTimezone().getFirstAndLastTimeOfWorkingTimezone();
				})
				.collect(Collectors.toList());

		if(lstWorkingTimezone.isEmpty()) return null;

		TimeSpanForCalc workingTimezone = TimeSpanForCalc.join(lstWorkingTimezone).get();
		// $1日 = [prv-1] 指定した午前午後区分の時間帯情報を作成する( $就業の時間帯, 1日 )
		val oneDay = this.createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.ONE_DAY);
		// $休出 = [prv-3] 休出時の時間帯を作成する()
		val workOnDayOff = createWorkOnDayOffTime(require);
		// if @半日用シフトを使用する == false	return 変更可能な勤務時間帯#半日の区別しない( $1日, $休出 )
		if (!this.isUseHalfDayShift()) {
			return ChangeableWorkingTimeZone.createWithoutSeparationOfHalfDay(oneDay, workOnDayOff);
		}

		// $午前 = [prv-1] 指定した午前午後区分の時間帯情報を作成する ($就業の時間帯, 午前)
		val morning = createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.AM);
		// $午後 = [prv-1] 指定した午前午後区分の時間帯情報を作成する ($就業の時間帯, 午後)
		val evening = createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.PM);

		return ChangeableWorkingTimeZone.create(oneDay, morning, evening, workOnDayOff);
	}

	/**
	 * 休憩時間帯を取得する
	 *
	 * @param isWorkingOnDayOff 休出か
	 * @param amPmAtr 午前午後区分
	 * @return 休憩時間
	 */
	@Override
	public BreakTimeZone getBreakTimeZone(boolean isWorkingOnDayOff, AmPmAtr amPmAtr) {
		FlowWorkRestTimezone breakTimeZone = null;
		if (isWorkingOnDayOff == true) {
			// $休憩時間帯 = @休日勤務時間帯.休憩時間帯
			breakTimeZone = this.offdayWorkTime.getRestTimezone();
		} else {
			Optional<FlowWorkRestTimezone> breakTimeZoneOpt = this.lstHalfDayWorkTimezone.stream()
					.filter(c -> c.getAmpmAtr() == amPmAtr)
					.map(c -> c.getRestTimezone())
					.findFirst();
			if (breakTimeZoneOpt.isPresent()) {
				breakTimeZone = breakTimeZoneOpt.get();
			}
		}

		// 流動休憩
		// if not $休憩時間帯.休憩時間帯を固定にする
		// return 休憩時間#流動休憩で作る( List.empty )
		if (!breakTimeZone.isFixRestTime()) {
			return BreakTimeZone.createAsNotFixed(Collections.emptyList());
		}

		// 固定休憩
		// return 休憩時間#固定休憩で作る( $休憩時間帯.固定休憩時間帯.休憩時間帯を取得() )
		return BreakTimeZone.createAsFixed(breakTimeZone.getFixedRestTimezone().getRestTimezonesForCalc());
	}

	/**
	 * [prv-1] 指定した午前午後区分の時間帯情報を作成する
	 * @param require Require
	 * @param wortime 就業の時間帯
	 * @param ampmAtr 午前午後区分
	 * @return
	 */
	private List<ChangeableWorkingTimeZonePerNo> createTimeZoneByAmPmCls(WorkSetting.Require require, TimeSpanForCalc wortime, AmPmAtr ampmAtr) {

		/**
		 * $残業の時間帯 = @平日勤務時間帯 : filter $.午前午後区分 == 午前午後区分 map $.勤務時間帯.残業の時間帯 ()
		 */
		List<TimeSpanForCalc> lstOTTimezone = this.lstHalfDayWorkTimezone.stream()
				.filter(c -> {
					return c.getAmpmAtr() == ampmAtr
							&& c.getWorkTimezone()!= null
							&& c.getWorkTimezone().getFirstAndLastTimeOfOvertimeWorkingTimezone().isPresent();
				})
				.map(c -> c.getWorkTimezone().getFirstAndLastTimeOfOvertimeWorkingTimezone().get())
				.collect(Collectors.toList());

		// $勤務可能時間帯 = 就業の時間帯
		TimeSpanForCalc wkTimePossibles = wortime;
		/**
		 * if $残業の時間帯.isPresent $勤務可能時間帯 = 時間帯#連結する (list: @残業の時間帯, 就業の時間帯)
		 */
		if (!lstOTTimezone.isEmpty()) {
			// $勤務可能時間帯 = 時間帯#連結する (list: @残業の時間帯, 就業の時間帯)
			lstOTTimezone.add(wortime);
			wkTimePossibles = TimeSpanForCalc.join(lstOTTimezone).get();
		}

		if (this.coreTimeSetting.getTimesheet() == ApplyAtr.NOT_USE) {
			val timezonePerNo = ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
					new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1),
					wkTimePossibles);
			return Arrays.asList(timezonePerNo);
		}

		val coreTime = getCoreTimeByAmPm(require, ampmAtr);
		val startTime = new TimeSpanForCalc(wkTimePossibles.getStart(), coreTime.getStart());
		val endTime = new TimeSpanForCalc(coreTime.getEnd(), wkTimePossibles.getEnd());

		return Arrays.asList(new ChangeableWorkingTimeZonePerNo(
				new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1), startTime,
				endTime));
	}

	/**
	 * [prv-2] 指定した午前午後区分のコアタイム時間帯を取得する
	 * @param require
	 * @param ampmAtr
	 * @return
	 */
	private TimeSpanForCalc getCoreTimeByAmPm(WorkSetting.Require require, AmPmAtr ampmAtr) {
		if (ampmAtr == AmPmAtr.ONE_DAY) {
			return new TimeSpanForCalc(this.coreTimeSetting.getCoreTimeSheet().getStartTime(),
					this.coreTimeSetting.getCoreTimeSheet().getEndTime());
		}

		val predetermineTimeSetting = require.getPredetermineTimeSetting(this.workTimeCode);

		switch (ampmAtr) {
			case AM:
				return new TimeSpanForCalc(this.coreTimeSetting.getCoreTimeSheet().getStartTime(),
						predetermineTimeSetting.getPrescribedTimezoneSetting().getMorningEndTime());
			case PM:
				return new TimeSpanForCalc(predetermineTimeSetting.getPrescribedTimezoneSetting().getAfternoonStartTime(),
						this.coreTimeSetting.getCoreTimeSheet().getEndTime());
			default: return null;
		}
	}

	/**
	 * [prv-3] 休出時の時間帯を作成する
	 * @param require
	 * @return
	 */
	private List<ChangeableWorkingTimeZonePerNo> createWorkOnDayOffTime(WorkSetting.Require require) {

		val predetermineTimeSetting = require.getPredetermineTimeSetting(this.workTimeCode);

		val workOnDayOffTimeList = this.offdayWorkTime.getLstWorkTimezone().stream()
				.map(c -> c.getTimezone().timeSpan())
				.collect(Collectors.toList());
		val workOnDayOffTime = TimeSpanForCalc.join(workOnDayOffTimeList);

		val workTimeList = predetermineTimeSetting.getTimezoneByAmPmAtrForCalc(AmPmAtr.ONE_DAY);

		if (checkDuplicate(workTimeList) == true) {
			return Collections.emptyList();
		}

		return Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
				new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1)
				, workOnDayOffTime.get()));
	}


	private boolean checkDuplicate(List<TimeSpanForCalc> timespans) {
		for(int i = 0; i < timespans.size() - 1; i++) {
			if (timespans.get(i).checkDuplication(timespans.get(i + 1)) == TimeSpanDuplication.NOT_DUPLICATE)
				return true;
		}
		return false;
	}


	public static interface Require {
	}

}
