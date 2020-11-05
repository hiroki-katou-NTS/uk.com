/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * The Class FlowWorkSetting.
 */
// 流動勤務設定
@Getter
@NoArgsConstructor
public class FlowWorkSetting extends WorkTimeAggregateRoot implements Cloneable, WorkSetting {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The working code. */
	// 就業時間帯コード
	private WorkTimeCode workingCode;

	/** The half day work timezone. */
	// 平日勤務時間帯
	private FlowHalfDayWorkTimezone halfDayWorkTimezone;

	/** The offday work timezone. */
	// 休日勤務時間帯
	private FlowOffdayWorkTimezone offdayWorkTimezone;

	/** The stamp reflect timezone. */
	// 打刻反映時間帯
	private FlowStampReflectTimezone stampReflectTimezone;

	/** The legal OT setting. */
	// 法定内残業設定
	private LegalOTSetting legalOTSetting;

	/** The rest setting. */
	// 休憩設定
	private FlowWorkRestSetting restSetting;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The flow setting. */
	// 流動設定
	private FlowWorkDedicateSetting flowSetting;

	/** 就業時間帯コード */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return this.workingCode;
	}

	/**
	 * Instantiates a new flow work setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public FlowWorkSetting(FlowWorkSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workingCode = memento.getWorkingCode();
		this.restSetting = memento.getRestSetting();
		this.offdayWorkTimezone = memento.getOffdayWorkTimezone();
		this.commonSetting = memento.getCommonSetting();
		this.halfDayWorkTimezone = memento.getHalfDayWorkTimezone();
		this.stampReflectTimezone = memento.getStampReflectTimezone();
		this.legalOTSetting = memento.getLegalOTSetting();
		this.flowSetting = memento.getFlowSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FlowWorkSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkingCode(this.workingCode);
		memento.setRestSetting(this.restSetting);
		memento.setOffdayWorkTimezone(this.offdayWorkTimezone);
		memento.setCommonSetting(this.commonSetting);
		memento.setHalfDayWorkTimezone(this.halfDayWorkTimezone);
		memento.setStampReflectTimezone(this.stampReflectTimezone);
		memento.setLegalOTSetting(this.legalOTSetting);
		memento.setFlowSetting(this.flowSetting);
	}

	/**
	 * Restore data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param workTimeType
	 *            the work time type
	 * @param other
	 *            the other
	 */
	public void correctData(ScreenMode screenMode, WorkTimeDivision workTimeType, FlowWorkSetting other) {
		// Dialog J: list stamp timezone
		this.stampReflectTimezone.correctData(screenMode, other.getStampReflectTimezone());

		// Tab 2 + 5 + 7
		if (workTimeType.getWorkTimeDailyAtr() == WorkTimeDailyAtr.REGULAR_WORK
				&& workTimeType.getWorkTimeMethodSet() == WorkTimeMethodSet.FLOW_WORK) {
			// Tab 2: restore 平日勤務時間帯
			this.flowSetting.correctData(screenMode, other.getFlowSetting());
			// Tab 5
			this.halfDayWorkTimezone.correctData(screenMode, other.getHalfDayWorkTimezone());
			// Tab 7
			this.offdayWorkTimezone.correctData(screenMode, other.getOffdayWorkTimezone());

			this.restSetting.correctData(screenMode,other.getRestSetting(),other.getHalfDayWorkTimezone().getRestTimezone().isFixRestTime());
		} else {
			// Tab 2
			this.flowSetting = other.getFlowSetting();
			// Tab 5
			this.halfDayWorkTimezone = other.getHalfDayWorkTimezone();
			// Tab 7
			this.offdayWorkTimezone = other.getOffdayWorkTimezone();
		}

		// Tab 8 -> 16
		this.commonSetting.correctData(screenMode, other.getCommonSetting());
	}

	/**
	 * Restore default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		// Dialog J: list stamp timezone
		this.stampReflectTimezone.correctDefaultData(screenMode);

		// Tab 2 + 5: restore 平日勤務時間帯
		this.halfDayWorkTimezone.correctDefaultData(screenMode);

		// Tab 7
		this.offdayWorkTimezone.correctDefaultData(screenMode);

		// Tab 8 -> 16
		this.commonSetting.correctDefaultData(screenMode);
		//Dialog H
		this.restSetting.correctDefaultData(screenMode,this.getHalfDayWorkTimezone().getRestTimezone().isFixRestTime());
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode the screen mode
	 * @param overtimeSetting the overtime setting
	 */
	public void setDefaultData(ScreenMode screenMode) {
		if (screenMode == ScreenMode.SIMPLE) {
			this.halfDayWorkTimezone.correctDefaultData();
		}
	}


	/**
 	 * create this Instance
 	 * TODO 必要に応じてcloneする変数を増やす。
	 * @return new Instance
	 */
	@Override
	public FlowWorkSetting clone() {
		FlowWorkSetting cloned = new FlowWorkSetting();
		try {
			cloned.companyId = this.companyId;
			cloned.workingCode = new WorkTimeCode(this.workingCode.v());
			cloned.offdayWorkTimezone = this.offdayWorkTimezone.clone();
			cloned.commonSetting = this.commonSetting.clone();
			cloned.restSetting = this.restSetting.clone();
			cloned.halfDayWorkTimezone = this.halfDayWorkTimezone.clone();
			cloned.stampReflectTimezone = this.stampReflectTimezone.clone();
			cloned.legalOTSetting = LegalOTSetting.valueOf(this.legalOTSetting.value);
			cloned.flowSetting = this.flowSetting.clone();
		}
		catch (Exception e){
			throw new RuntimeException("FlowWorkSetting clone error.");
		}
		return cloned;
	}

	/**
	 * 平日勤務時間帯.勤務時間帯.残業時間帯を取得する(就業時間帯NOの昇順）
	 * @return 残業時間帯
	 */
	public List<FlowOTTimezone> getHalfDayWorkTimezoneLstOTTimezone() {
		List<FlowOTTimezone> lstOTTimezone = this.halfDayWorkTimezone.getWorkTimeZone().getLstOTTimezone();
		lstOTTimezone.sort((f,s) -> f.getWorktimeNo().compareTo(s.getWorktimeNo()));
		return lstOTTimezone;
	}

	/**
	 * 平日勤務時間帯.勤務時間帯.休出時間帯を取得する(就業時間帯NOの昇順）
	 * @return 休出時間帯
	 */
	public List<FlowWorkHolidayTimeZone> getOffdayWorkTimezoneLstWorkTimezone() {
		return this.offdayWorkTimezone.getLstWorkTimezone().stream()
				.sorted((f,s) -> f.getWorktimeNo().compareTo(s.getWorktimeNo()))
				.collect(Collectors.toList());
	}

	/**
	 * 勤務種類から流動勤務の休憩時間帯を取得する
	 * @param workType
	 * @return 流動勤務の休憩時間帯
	 */
	public FlowWorkRestTimezone getFlowWorkRestTimezone(WorkType workType) {
		if(workType.getDailyWork().isHolidayWork()) {
			return this.offdayWorkTimezone.getRestTimeZone();
		}
		return this.halfDayWorkTimezone.getRestTimezone();
	}

	/**
	 * 勤務種類から就業時間帯Noと法定内残業枠Noを取得する
	 * @return Map<就業時間帯No, 法定内の残業枠No>
	 */
	public Map<EmTimezoneNo, OverTimeFrameNo> getLegalOverTimeFrameNoMap() {
		return this.getHalfDayWorkTimezoneLstOTTimezone().stream()
				//就業時間帯の残業枠はOvertimeWorkFrameNoになっている為、OverTimeFrameNoへ変換する必要がある。
				.collect(Collectors.toMap(k->new EmTimezoneNo(k.getWorktimeNo()), v->new OverTimeFrameNo(v.getInLegalOTFrameNo().v().intValue())));
	}


	/**
	 * 変更可能な勤務時間帯を取得する
	 * @param require Require
	 * @return 変更可能な時間帯
	 */
	@Override
	public ChangeableWorkingTimeZone getChangeableWorkingTimeZone(WorkSetting.Require require) {
		// 所定時間設定を取得																																			
		// $所定時間設定 = [1] 所定時間設定を取得する( require )	
		val predetemineTimeSetting = require.getPredetermineTimeSetting(this.getWorkTimeCode());
		
		// 勤務可能な時間帯を取得																																		
		// $1日の勤務可能時間 = $所定時間設定.勤務時間範囲を取得する( 午前午後区分.1日 )																						
		// $午前の勤務可能時間 = $所定時間設定.勤務時間範囲を取得する( 午前午後区分.午前 )																						
		// $午後の勤務可能時間 = $所定時間設定.勤務時間範囲を取得する( 午前午後区分.午後 )
		val workAbleTimeZoneOfOneDay = predetemineTimeSetting.getOneDaySpan();
		val workAbleTimeZoneOfMorning = predetemineTimeSetting.getHalfDayOfAmSpan();
		val workAbleTimeZoneOfEvening = predetemineTimeSetting.getHalfDayOfPmSpan();
		
		// 1勤目																																						
		// $1日リスト = list: 勤務NOごとの変更可能な勤務時間帯#開始と終了が同じ (1, $1日の勤務可能時間)																			
		// $午前リスト = list: 勤務NOごとの変更可能な勤務時間帯#開始と終了が同じ (1, $午前の勤務可能時間)																		
		// $午後リスト = list: 勤務NOごとの変更可能な勤務時間帯#開始と終了が同じ (1, $午後の勤務可能時間)
		val oneDayList = Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
				new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1),
				workAbleTimeZoneOfOneDay));
		val morningList = Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
				new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1),
				workAbleTimeZoneOfMorning
				));
		
		val evenningList = Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
				new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(1),
				workAbleTimeZoneOfEvening
				));
		
		
		// 2勤目																																						
		// if $所定時間設定.2回勤務か ()																																	
		// $1日リスト.add (勤務NOごとの変更可能な勤務時間帯#開始と終了が同じ (2, $1日の勤務可能時間) )																		
		// $午前リスト.add (勤務NOごとの変更可能な勤務時間帯#開始と終了が同じ (2, $午前の勤務可能時間) )																		
		// $午後リスト.add (勤務NOごとの変更可能な勤務時間帯#開始と終了が同じ (2, $午後の勤務可能時間) )
		if(predetemineTimeSetting.isUseShiftTwo()) {
			
			oneDayList.add(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
				new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(2),
				workAbleTimeZoneOfOneDay));
			morningList.add(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
					new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(2),
					workAbleTimeZoneOfMorning));
			evenningList.add(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd(
					new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo(2),
					workAbleTimeZoneOfEvening));
		}
		
		//return 勤務可能な時間帯情報 ($1日リスト, $午前リスト, $午後リスト, $1日リスト)
		return new ChangeableWorkingTimeZone(oneDayList, morningList, evenningList, oneDayList);
	}

	/**
	 * 休憩時間帯を取得する
	 * @param isWorkingOnDayOff 休出か
	 * @param amPmAtr 午前午後区分
	 * @return 休憩時間
	 */
	@Override
	public BreakTimeZone getBreakTimeZone(boolean isWorkingOnDayOff, AmPmAtr amPmAtr) {
		FlowWorkRestTimezone breakTimeZone = null;
		if(isWorkingOnDayOff) {
			//$休憩時間帯 = @休日勤務時間帯	
			breakTimeZone = this.offdayWorkTimezone.getRestTimeZone();
		}else {
			//$休憩時間帯 = @平日勤務時間帯
			breakTimeZone = halfDayWorkTimezone.getRestTimezone();
		}
		
		//if not $休憩時間帯.休憩時間帯を固定にする
		if(breakTimeZone.isFixRestTime()) {
			//return 休憩時間#流動休憩で作る( List.empty )
			return BreakTimeZone.createAsNotFixed(Collections.emptyList());
		}
		
		// 固定休憩
		// return 休憩時間#固定休憩で作る( $休憩時間帯.固定休憩時間帯.休憩時間帯を取得() )
		return BreakTimeZone.createAsFixed(breakTimeZone.getFixedRestTimezone()
				                                        .getRestTimezonesForCalc());
	}


	public static interface Require {
	}
}
