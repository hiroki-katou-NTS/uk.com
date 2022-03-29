/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.*;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.HalfDayWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	private HalfDayWorkSet useHalfDayShift;

	/** The lst half day work timezone. */
	// 平日勤務時間帯
	private List<FlexHalfDayWorkTime> lstHalfDayWorkTimezone;

	/** The lst stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> lstStampReflectTimezone;

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
		this.lstStampReflectTimezone = memento.getLstStampReflectTimezone();
		
		if (!this.validateHalfDayWorkTime(memento.getLstHalfDayWorkTimezone())) {
		    throw new BusinessException("Msg_2143");
		}
		this.lstHalfDayWorkTimezone = memento.getLstHalfDayWorkTimezone();
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
		memento.setLstStampReflectTimezone(this.lstStampReflectTimezone);
		
		if (!this.validateHalfDayWorkTime(this.lstHalfDayWorkTimezone)) {
            throw new BusinessException("Msg_2143");
        }
		memento.setLstHalfDayWorkTimezone(this.lstHalfDayWorkTimezone);
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
		
		this.lstHalfDayWorkTimezone
		    .forEach(x -> x.getRestTimezone().correctData(
		            other.getLstHalfDayWorkTimezone().stream()
		            .filter(y -> y.getAmpmAtr().value == x.getAmpmAtr().value).findFirst().get().getRestTimezone()));
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
			cloned.useHalfDayShift = this.useHalfDayShift;
			cloned.lstHalfDayWorkTimezone = this.lstHalfDayWorkTimezone.stream().map(c -> c.clone())
					.collect(Collectors.toList());
			cloned.lstStampReflectTimezone = this.lstStampReflectTimezone.stream().map(c -> c.clone())
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException("AggregateTotalTimeSpentAtWork clone error.");
		}
		return cloned;
	}

	public static interface Require {
		Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode);
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
		val attenHolAtr = workType.getAttendanceHolidayAttr();
		if (workType.isHolidayWork()) {
			return this.offdayWorkTime.getRestTimezone();
		}
		if (this.getFlexHalfDayWorkTime(attenHolAtr).isPresent()) {
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
				.map(c ->  c.getWorkTimezone().getFirstAndLastTimeOfWorkingTimezone())
				.findFirst().get();

		val oneDay = this.createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.ONE_DAY);
		val workOnDayOff = this.createWorkOnDayOffTime(require);

		val morning = this.createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.AM);
		val evening = this.createTimeZoneByAmPmCls(require, workingTimezone, AmPmAtr.PM);

		return ChangeableWorkingTimeZone.create(oneDay, morning, evening, workOnDayOff);
	}

	/**
	 * 休憩時間帯を取得する
	 * @param isWorkingOnDayOff 休出か
	 * @param amPmAtr 午前午後区分
	 * @return 休憩時間
	 */
	@Override
	public BreakTimeZone getBreakTimeZone(boolean isWorkingOnDayOff, AmPmAtr amPmAtr) {
		FlowWorkRestTimezone breakTimeZone;
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
	 * 指定した午前午後区分のコアタイム時間帯を取得する
	 * @param require require
	 * @param ampmAtr 午前午後区分
	 * @return
	 */
	public Optional<TimeSpanForCalc> getCoreTimeByAmPmForCalc(WorkSetting.Require require, AmPmAtr ampmAtr) {

		// コアタイム時間帯を使用しない⇒empty
		if ( !this.coreTimeSetting.isUseTimeSheet() ) {
			return Optional.empty();
		}

		// コアタイム時間帯を取得
		val timespan = this.coreTimeSetting.getCoreTimeSheet().timespan();

		// 1日⇒設定をそのまま返す
		if ( ampmAtr == AmPmAtr.ONE_DAY ) {
			return Optional.of( timespan );
		}

		// 半日⇒コアタイムを午前終了時刻/午後開始時刻で分割する
		// ※コアタイムは必ず午前終了時刻/午後開始時刻を内包するため
		val predetTimeStg = this.getPredetermineTimeSetting(require).get();
		switch (ampmAtr) {
			case AM:
				// 午前⇒終了時刻:午前終了時刻
				return Optional.of( timespan.shiftOnlyEnd( predetTimeStg.getPrescribedTimezoneSetting().getMorningEndTime() ) );
			case PM:
				// 午後⇒開始時刻:午後開始時刻
				return Optional.of( timespan.shiftOnlyStart( predetTimeStg.getPrescribedTimezoneSetting().getAfternoonStartTime() ) );
			default:
				throw new RuntimeException("AmPmAtrは、ONE_DAY、AM、PMの3種類しかなく、システムエラー");
		}

	}


	/**
	 * 指定した午前午後区分の時間帯情報を作成する
	 * @param require Require
	 * @param wortkime 就業の時間帯
	 * @param ampmAtr 午前午後区分
	 * @return
	 */
	private List<ChangeableWorkingTimeZonePerNo> createTimeZoneByAmPmCls(WorkSetting.Require require, TimeSpanForCalc wortkime, AmPmAtr ampmAtr) {

		// 残業を含めた勤務可能な時間帯を取得する
		List<TimeSpanForCalc> lstOTTimezone = this.lstHalfDayWorkTimezone.stream()
				.filter(c -> c.getAmpmAtr() == ampmAtr )
				.map(c -> c.getWorkTimezone().getFirstAndLastTimeOfOvertimeWorkingTimezone())
				.flatMap(OptionalUtil::stream)
				.collect(Collectors.toList());
		lstOTTimezone.add(wortkime);
		val wkTimePossibles = TimeSpanForCalc.join(lstOTTimezone).get();

		// コアタイムを使用しない⇒開始/終了が同じ
		if ( !this.coreTimeSetting.isUseTimeSheet() ) {
			return Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(new WorkNo(1), wkTimePossibles));
		}

		// コアタイムを使用する⇒コアタイムの開始/終了で分割する
		val coreTime = this.getCoreTimeByAmPmForCalc(require, ampmAtr).get();
		val startTime = new TimeSpanForCalc(wkTimePossibles.getStart(), coreTime.getStart());
		val endTime = new TimeSpanForCalc(coreTime.getEnd(), wkTimePossibles.getEnd());
		return Arrays.asList(ChangeableWorkingTimeZonePerNo.create(new WorkNo(1), startTime, endTime));

	}


	/**
	 * 休出時の時間帯を作成する
	 * @param require
	 * @return
	 */
	private List<ChangeableWorkingTimeZonePerNo> createWorkOnDayOffTime(WorkSetting.Require require) {

		val preTimeSetting =  this.getPredetermineTimeSetting(require).get();

		val workOnDayOffTimeList = this.offdayWorkTime.getLstWorkTimezone().stream()
				.map(c -> c.getTimezone().timeSpan())
				.collect(Collectors.toList());
		// 休出時間がないことはないので、直接get
		val workOnDayOffTime = TimeSpanForCalc.join(workOnDayOffTimeList).get();

		val workTimeList = preTimeSetting.getTimezoneByAmPmAtrForCalc(AmPmAtr.ONE_DAY);


		if (workTimeList.stream().allMatch(c -> c.checkDuplication(workOnDayOffTime) == TimeSpanDuplication.NOT_DUPLICATE)) {
			return Collections.emptyList();
		}

		return Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
				new WorkNo(1), workOnDayOffTime));
	}
	
	/**
     * inv-1
     */
    private boolean validateHalfDayWorkTime(List<FlexHalfDayWorkTime> lstHalfDayWorkTimezone){
        val onlyOneAllDay = lstHalfDayWorkTimezone
                .stream()
                .filter(tz -> tz.getAmpmAtr() == AmPmAtr.ONE_DAY)
                .count() == 1;
        val onlyOneAM = lstHalfDayWorkTimezone
                .stream()
                .filter(tz -> tz.getAmpmAtr() == AmPmAtr.AM)
                .count() == 1;
        val onlyOnePM = lstHalfDayWorkTimezone
                .stream()
                .filter(tz -> tz.getAmpmAtr() == AmPmAtr.PM)
                .count() == 1;
        return onlyOneAllDay && onlyOneAM && onlyOnePM;
    }
	
	/**
	 * コア内の外出を就業時間から控除するか判断する
	 * @return 控除するかどうか（true:控除する、false:控除しない）
	 */
	public boolean isDeductGoOutWithinCoreFromWorkTime(){
		if (this.coreTimeSetting.isUseTimeSheet() == false) return true;
		if (this.coreTimeSetting.getGoOutCalc().getRemoveFromWorkTime() == NotUseAtr.USE) return true;
		return false;
	}
}
