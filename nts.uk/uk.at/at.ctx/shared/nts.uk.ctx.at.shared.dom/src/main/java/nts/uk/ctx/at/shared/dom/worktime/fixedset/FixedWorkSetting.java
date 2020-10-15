/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.FixedWorkRestSet;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * The Class FixedWorkSetting.
 */
@Getter
@NoArgsConstructor
// 固定勤務設定
public class FixedWorkSetting extends WorkTimeAggregateRoot implements Cloneable{

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The work time code. */
	// 就業時間帯コード
	private WorkTimeCode workTimeCode;

	/** The offday work timezone. */
	// 休日勤務時間帯
	private FixOffdayWorkTimezone offdayWorkTimezone;

	/** The common setting. */
	// 共通設定
	private WorkTimezoneCommonSet commonSetting;

	/** The use half day shift. */
	// 半日用シフトを使用する
	private Boolean useHalfDayShift;

	/** The fixed work rest setting. */
	// 固定勤務の休憩設定
	private FixedWorkRestSet fixedWorkRestSetting;

	/** The lst half day work timezone. */
	// 平日勤務時間帯
	private List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone;

	/** The lst stamp reflect timezone. */
	// 打刻反映時間帯
	private List<StampReflectTimezone> lstStampReflectTimezone;

	/** The legal OT setting. */
	// 法定内残業設定
	private LegalOTSetting legalOTSetting;

	/** The calculation setting. */
	// 計算設定
	private Optional<FixedWorkCalcSetting> calculationSetting;

	/**
	 * Instantiates a new fixed work setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public FixedWorkSetting(FixedWorkSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workTimeCode = memento.getWorkTimeCode();
		this.offdayWorkTimezone = memento.getOffdayWorkTimezone();
		this.commonSetting = memento.getCommonSetting();
		this.useHalfDayShift = memento.getUseHalfDayShift();
		this.fixedWorkRestSetting = memento.getFixedWorkRestSetting();
		this.lstHalfDayWorkTimezone = memento.getLstHalfDayWorkTimezone();
		this.lstStampReflectTimezone = memento.getLstStampReflectTimezone();
		this.legalOTSetting = memento.getLegalOTSetting();
		this.calculationSetting = memento.getCalculationSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FixedWorkSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setOffdayWorkTimezone(this.offdayWorkTimezone);
		memento.setCommonSetting(this.commonSetting);
		memento.setUseHalfDayShift(this.useHalfDayShift);
		memento.setFixedWorkRestSetting(this.fixedWorkRestSetting);
		memento.setLstHalfDayWorkTimezone(this.lstHalfDayWorkTimezone);
		memento.setLstStampReflectTimezone(this.lstStampReflectTimezone);
		memento.setLegalOTSetting(this.legalOTSetting);
		memento.setCalculationSetting(this.calculationSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((workTimeCode == null) ? 0 : workTimeCode.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FixedWorkSetting other = (FixedWorkSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (workTimeCode == null) {
			if (other.workTimeCode != null)
				return false;
		} else if (!workTimeCode.equals(other.workTimeCode))
			return false;
		return true;
	}

	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param workTimeType
	 *            the work time type
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, WorkTimeDivision workTimeType, FixedWorkSetting oldDomain) {		
		// Dialog J: list stamp timezone
		Map<Entry<WorkNo, GoLeavingWorkAtr>, StampReflectTimezone> mapStampReflectTimezone = oldDomain.getLstStampReflectTimezone().stream()
				.collect(Collectors.toMap(
						item -> new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()), 
						Function.identity()));
		this.lstStampReflectTimezone.forEach(item -> item.correctData(screenMode, mapStampReflectTimezone.get(
				new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()))));
		
		// Tab 8 -> 16
		this.commonSetting.correctData(screenMode, oldDomain.getCommonSetting());
		
		// Tab 17
		if (this.calculationSetting.isPresent()) {
			this.calculationSetting.get().correctData(screenMode, oldDomain.getCalculationSetting());
		}		
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		// Tab 2 + 3 + 5: restore 平日勤務時間帯
		this.lstHalfDayWorkTimezone.forEach(item -> item.correctDefaultData(screenMode));
		
		// Dialog J: list stamp timezone
		this.lstStampReflectTimezone.forEach(item -> item.correctDefaultData(screenMode));

		// Tab 8 -> 16
		this.commonSetting.correctDefaultData(screenMode);
		
		// Tab 17
		if (this.calculationSetting.isPresent()) {
			this.calculationSetting.get().correctDefaultData(screenMode);
		}	
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode the screen mode
	 * @param overtimeSetting the overtime setting
	 */
	public void setDefaultData(ScreenMode screenMode) {
		if (screenMode == ScreenMode.SIMPLE || this.legalOTSetting == LegalOTSetting.OUTSIDE_LEGAL_TIME) {
			this.lstHalfDayWorkTimezone.forEach(item -> item.correctDefaultData());
		}
	}

	/**
 	 * create this Instance
 	 * TODO 必要に応じてcloneする変数を増やす。
	 * @return new Instance
	 */
	@Override
	public FixedWorkSetting clone() {
		FixedWorkSetting cloned = new FixedWorkSetting();
		try {
			cloned.companyId = this.companyId;
			cloned.workTimeCode = new WorkTimeCode(this.workTimeCode.v());
			cloned.offdayWorkTimezone = this.offdayWorkTimezone.clone();
			cloned.commonSetting = this.commonSetting.clone();
			cloned.useHalfDayShift = this.useHalfDayShift ? true : false;
			cloned.fixedWorkRestSetting = this.fixedWorkRestSetting.clone();
			cloned.lstHalfDayWorkTimezone = this.lstHalfDayWorkTimezone.stream().map(c -> c.clone()).collect(Collectors.toList());
			cloned.lstStampReflectTimezone = this.lstStampReflectTimezone.stream().map(c -> c.clone()).collect(Collectors.toList());
			cloned.legalOTSetting = LegalOTSetting.valueOf(this.legalOTSetting.value);
			if(this.calculationSetting.isPresent()) {
				cloned.calculationSetting = this.calculationSetting.map(c -> c.clone());
			}
			else {
				cloned.calculationSetting = Optional.empty();
			}
		}
		catch (Exception e){
			throw new RuntimeException("FixedWorkSetting clone error.");
		}
		return cloned;
	}
	
	/**
	 * 勤務種類から就業時間帯Noと法定内残業枠Noを取得する
	 * @param workType 勤務種類
	 * @return Map<就業時間帯No, 法定内の残業枠No>
	 */
	public Map<EmTimezoneNo, OverTimeFrameNo> getLegalOverTimeFrameNoMap(WorkType workType) {
		return this.getOverTimeOfTimeZoneSet(workType).stream()
				//就業時間帯の残業枠はOTFrameNoになっている為、OverTimeFrameNoへ変換する必要がある。
				.collect(Collectors.toMap(k->k.getWorkTimezoneNo(), v->new OverTimeFrameNo(v.getLegalOTframeNo().v())));
	}
	
	/**
	 * 勤務種類から法定内残業枠Noを取得する
	 * @param workType 勤務種類
	 * @return 法定内の残業枠No(List)
	 */
	public List<OverTimeFrameNo> getLegalOverTimeFrameNoList(WorkType workType) {
		return this.getOverTimeOfTimeZoneSet(workType).stream()
				.map(overTimeOfTimeZoneSet -> overTimeOfTimeZoneSet.getLegalOTframeNo())
				.map(oTframeNo -> new OverTimeFrameNo(oTframeNo.v())) //就業時間帯の残業枠はOTFrameNoになっている為、OverTimeFrameNoへ変換する必要がある。
				.collect(Collectors.toList());
	}
	
	/**
	 * 勤務種類から就業時間の時間帯設定を取得する
	 * @param workType 勤務種類
	 * @return 就業時間の時間帯設定(List)
	 */
	public List<EmTimeZoneSet> getEmTimeZoneSet(WorkType workType) {
		return this.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).isPresent()
					?this.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).get().getWorkTimezone().getLstWorkingTimezone()
					:Collections.emptyList();
	}
	
	/**
	 * 勤務種類から残業時間の時間帯設定を取得する
	 * @param workType 勤務種類
	 * @return 残業時間の時間帯設定(List)
	 */
	public List<OverTimeOfTimeZoneSet> getOverTimeOfTimeZoneSet(WorkType workType) {
		return this.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).isPresent()
					?this.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).get().getWorkTimezone().getLstOTTimezone()
					:Collections.emptyList();
	}
	
	/**
	 * 出勤休日区分から固定勤務の平日出勤用勤務時間帯を取得する
	 * 
	 * @param attendanceHolidayAttr 出勤休日区分
	 * @return 固定勤務の平日出勤用勤務時間帯(List)
	 */
	private Optional<FixHalfDayWorkTimezone> getFixHalfDayWorkTimezone(AttendanceHolidayAttr attendanceHolidayAttr) {
		switch(attendanceHolidayAttr) {
		case FULL_TIME:	return this.getFixHalfDayWorkTimezone(AmPmAtr.ONE_DAY);
		case MORNING:		return this.getFixHalfDayWorkTimezone(AmPmAtr.AM);
		case AFTERNOON:	return this.getFixHalfDayWorkTimezone(AmPmAtr.PM);
		case HOLIDAY:		return Optional.empty();
		default:			throw new RuntimeException("unknown AttendanceHolidayAttr");
		}
	}
	
	/**
	 * 午前午後区分から固定勤務の平日出勤用勤務時間帯を取得する
	 * 
	 * @param amPmAtr 午前午後区分
	 * @return 固定勤務の平日出勤用勤務時間帯(List)
	 */
	private Optional<FixHalfDayWorkTimezone> getFixHalfDayWorkTimezone(AmPmAtr amPmAtr){
		return this.lstHalfDayWorkTimezone.stream().filter(timeZone -> timeZone.getDayAtr().equals(amPmAtr)).findFirst();
	}
}
