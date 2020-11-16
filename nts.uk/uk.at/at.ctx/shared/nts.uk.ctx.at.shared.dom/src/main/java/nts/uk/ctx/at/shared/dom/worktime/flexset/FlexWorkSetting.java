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
import nts.gul.util.OptionalUtil;
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

	@Override
	public WorkTimeCode getWorkTimeCode() {
		return this.workTimeCode;
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
		TimeSpanForCalc workingTimezone = this.lstHalfDayWorkTimezone.stream()
				.filter(c -> c.getAmpmAtr() == AmPmAtr.ONE_DAY)
				.map(c -> {
					return c.getWorkTimezone().getFirstAndLastTimeOfWorkingTimezone();
				})
				.findFirst().get();
		
		val oneDay = this.createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.ONE_DAY);
		val workOnDayOff = this.createWorkOnDayOffTime(require);
		if (!this.isUseHalfDayShift()) {
			return ChangeableWorkingTimeZone.createWithoutSeparationOfHalfDay(oneDay, workOnDayOff);
		}
		
		val morning = this.createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.AM);
		val evening = this.createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.PM);

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
		FlowWorkRestTimezone breakTimeZone = new FlowWorkRestTimezone();
		if (isWorkingOnDayOff) {
			breakTimeZone = this.offdayWorkTime.getRestTimezone();
		} else {
			//休憩時間帯が存在しないのは想定外なので、emptyチェックしない。
			breakTimeZone = this.lstHalfDayWorkTimezone.stream()
					.filter(c -> c.getAmpmAtr() == amPmAtr)
					.map(c -> c.getRestTimezone())
					.findFirst().get();
		}
		
		if (!breakTimeZone.isFixRestTime()) {
			return BreakTimeZone.createAsNotFixed(Collections.emptyList());
		}
		
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
		List<TimeSpanForCalc> lstOTTimezone = this.lstHalfDayWorkTimezone.stream()
				.filter(c -> c.getAmpmAtr() == ampmAtr )
				.map(c -> c.getWorkTimezone().getFirstAndLastTimeOfOvertimeWorkingTimezone())
				.flatMap(OptionalUtil::stream)
				.collect(Collectors.toList());
		
		TimeSpanForCalc wkTimePossibles = wortime;
		lstOTTimezone.add(wortime);
		wkTimePossibles = TimeSpanForCalc.join(lstOTTimezone).get();
		
		if (this.coreTimeSetting.getTimesheet() == ApplyAtr.NOT_USE) {
			return Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
					    new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1)
					  , wkTimePossibles)
					);
		}

		val coreTime = this.getCoreTimeByAmPm(require, ampmAtr);
		val startTime = new TimeSpanForCalc(wkTimePossibles.getStart(), coreTime.getStart());
		val endTime = new TimeSpanForCalc(coreTime.getEnd(), wkTimePossibles.getEnd());

		return Arrays.asList(new ChangeableWorkingTimeZonePerNo(
				  	  new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1)
					, startTime
					, endTime)
				);
	}

	/**
	 * [prv-2] 指定した午前午後区分のコアタイム時間帯を取得する
	 * @param require
	 * @param ampmAtr 午前午後区分
	 * @return
	 */
	private TimeSpanForCalc getCoreTimeByAmPm(WorkSetting.Require require, AmPmAtr ampmAtr) {
		if (ampmAtr == AmPmAtr.ONE_DAY) {
			return new TimeSpanForCalc( this.coreTimeSetting.getCoreTimeSheet().getStartTime(),
										this.coreTimeSetting.getCoreTimeSheet().getEndTime());
		}

		val predetermineTimeSetting = this.getPredetermineTimeSetting(require);

		switch (ampmAtr) {
			case AM:
				return new TimeSpanForCalc(	  this.coreTimeSetting.getCoreTimeSheet().getStartTime()
						   					, predetermineTimeSetting.getPrescribedTimezoneSetting().getMorningEndTime());
			case PM:
				return new TimeSpanForCalc(   predetermineTimeSetting.getPrescribedTimezoneSetting().getAfternoonStartTime()
											, this.coreTimeSetting.getCoreTimeSheet().getEndTime());
			default: 
				throw new RuntimeException("AmPmAtrは、ONE_DAY、AM、PMの3種類しかなく、システムエラー");
		}
	}

	/**
	 * [prv-3] 休出時の時間帯を作成する
	 * @param require
	 * @return
	 */
	private List<ChangeableWorkingTimeZonePerNo> createWorkOnDayOffTime(WorkSetting.Require require) {

		val preTimeSetting =  this.getPredetermineTimeSetting(require);

		val workOnDayOffTimeList = this.offdayWorkTime.getLstWorkTimezone().stream()
				.map(c -> c.getTimezone().timeSpan())
				.collect(Collectors.toList());
		val workOnDayOffTime = TimeSpanForCalc.join(workOnDayOffTimeList);
		
		val workTimeList = preTimeSetting.getTimezoneByAmPmAtrForCalc(AmPmAtr.ONE_DAY);

		
		if (workTimeList.stream().allMatch(c -> c.checkDuplication(workOnDayOffTime.get()) == TimeSpanDuplication.NOT_DUPLICATE)) {
			return Collections.emptyList();
		}

		return Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
				  new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1), workOnDayOffTime.get()));
	}
}
