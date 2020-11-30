/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 所定時間設定
 * The Class PredetemineTimeSetting.
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.共通設定.所定時間.所定時間設定
 */
@Getter
@NoArgsConstructor
public class PredetemineTimeSetting extends WorkTimeAggregateRoot implements Cloneable, Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The range time day. */
	// １日の範囲時間
	private AttendanceTime rangeTimeDay;

	/** The work time code. */
	// コード
	private WorkTimeCode workTimeCode;

	/** The pred time. */
	// 所定時間
	private PredetermineTime predTime;

	/** The night shift. */
	// 夜勤区分
	private boolean nightShift;

	/** The prescribed timezone setting. */
	// 所定時間帯
	private PrescribedTimezoneSetting prescribedTimezoneSetting;

	/** The start date clock. */
	// 日付開始時刻
	private TimeWithDayAttr startDateClock;

	/** The predetermine. */
	// 残業を含めた所定時間帯を設定する
	private boolean predetermine;

	/**
	 * Instantiates a new predetemine time setting.
	 *
	 * @param memento the memento
	 */
	public PredetemineTimeSetting(PredetemineTimeSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.rangeTimeDay = memento.getRangeTimeDay();
		this.workTimeCode = memento.getWorkTimeCode();
		this.predTime = memento.getPredTime();
		this.nightShift = memento.isNightShift();
		this.prescribedTimezoneSetting = memento.getPrescribedTimezoneSetting();
		this.startDateClock = memento.getStartDateClock();
		this.predetermine = memento.isPredetermine();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(PredetemineTimeSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setRangeTimeDay(this.rangeTimeDay);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setPredTime(this.predTime);
		memento.setNightShift(this.nightShift);
		memento.setPrescribedTimezoneSetting(this.prescribedTimezoneSetting);
		memento.setStartDateClock(this.startDateClock);
		memento.setPredetermine(this.predetermine);
	}

	/**
	 * Constructor
	 */
	public PredetemineTimeSetting(String companyId, AttendanceTime rangeTimeDay, WorkTimeCode workTimeCode,
			PredetermineTime predTime, boolean nightShift, PrescribedTimezoneSetting prescribedTimezoneSetting,
			TimeWithDayAttr startDateClock, boolean predetermine) {
		super();
		this.companyId = companyId;
		this.rangeTimeDay = rangeTimeDay;
		this.workTimeCode = workTimeCode;
		this.predTime = predTime;
		this.nightShift = nightShift;
		this.prescribedTimezoneSetting = prescribedTimezoneSetting;
		this.startDateClock = startDateClock;
		this.predetermine = predetermine;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		// Validate startDateClock in -12:00 ~ 23:59
		if ((this.startDateClock.valueAsMinutes() < TimeWithDayAttr.THE_PREVIOUS_DAY_1200.valueAsMinutes())
				|| (this.startDateClock.valueAsMinutes() >= TimeWithDayAttr.THE_NEXT_DAY_0000.valueAsMinutes())) {
			this.bundledBusinessExceptions.addMessage("Msg_785");
		}
		// Validate oneDay < rangeTimeDay
		this.validateOneDay();
		// Validate PrescribedTimezone between startDate and startDate + rangeTimeDay
		this.validatePrescribedTimezone();

		super.validate();
	}

	/**
	 * Validate prescribed timezone.
	 */
	private void validatePrescribedTimezone() {
		val timezones = this.prescribedTimezoneSetting.getLstTimezone();
		// validate list time zone
		if (timezones.stream()
				.anyMatch(tz -> tz.getUseAtr() == UseSetting.USE && this.isOutOfRangeTimeDay(tz.getStart(), tz.getEnd()))) {
			this.bundledBusinessExceptions.addMessage("Msg_516" , "KMK003_216");
		}
	}

	/**
	 * Checks if is out of range time day.
	 *
	 * @param start the start
	 * @param end the end
	 * @return true, if is out of range time day
	 */
	private boolean isOutOfRangeTimeDay(TimeWithDayAttr start, TimeWithDayAttr end) {
		val endDateClock = this.getEndDateClock();
		return start.lessThan(this.startDateClock) || start.greaterThan(endDateClock)
				|| end.lessThan(this.startDateClock) || end.greaterThan(endDateClock);
	}

	/**
	 * Validate one day.
	 */
	private void validateOneDay() {
		AttendanceTime oneDayRange = this.getRangeTimeDay();
		// Pred time
		AttendanceTime oneDayTime = this.getPredTime().getPredTime().getOneDay();
		if (oneDayTime.greaterThan(oneDayRange)) {
			this.bundledBusinessExceptions.addMessage("Msg_781");
		}
		AttendanceTime morningTime = this.getPredTime().getPredTime().getMorning();
		if (morningTime.greaterThan(oneDayRange)) {
			this.bundledBusinessExceptions.addMessage("Msg_781");
		}
		AttendanceTime afternoonTime = this.getPredTime().getPredTime().getAfternoon();
		if (afternoonTime.greaterThan(oneDayRange)) {
			this.bundledBusinessExceptions.addMessage("Msg_781");
		}
		// Add time
		AttendanceTime oneDayAddTime = this.getPredTime().getAddTime().getOneDay();
		if (oneDayAddTime.greaterThan(oneDayRange)) {
			this.bundledBusinessExceptions.addMessage("Msg_781");
		}
		AttendanceTime morningAddTime = this.getPredTime().getAddTime().getMorning();
		if (morningAddTime.greaterThan(oneDayRange)) {
			this.bundledBusinessExceptions.addMessage("Msg_781");
		}
		AttendanceTime afternoonAddTime = this.getPredTime().getAddTime().getAfternoon();
		if (afternoonAddTime.greaterThan(oneDayRange)) {
			this.bundledBusinessExceptions.addMessage("Msg_781");
		}
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PredetemineTimeSetting))
			return false;
		PredetemineTimeSetting other = (PredetemineTimeSetting) obj;
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
	 * Restore disabled data from.
	 *
	 * @param domain the domain
	 */
	public void restoreDisabledDataFrom(PredetemineTimeSetting domain) {
		// check predetermine is TRUE
		if (this.predetermine) {
			this.prescribedTimezoneSetting.restoreDisabledDataFrom(domain.getPrescribedTimezoneSetting());
		}
	}


	/**
	 * 日付終了時刻
	 * Gets the end date clock.
	 * @return the end date clock
	 */
	public TimeWithDayAttr getEndDateClock() {
		return this.startDateClock.forwardByMinutes(this.rangeTimeDay.valueAsMinutes());
	}


	/**
	 * 勤務時間範囲を取得する
	 * @param atr 午前午後区分
	 * @return 勤務可能な時間帯
	 */
	public TimeSpanForCalc getWorkableTimeSpan(AmPmAtr atr) {

		switch( atr ) {
			case ONE_DAY:
				return new TimeSpanForCalc( this.startDateClock, this.getEndDateClock() );
			case AM:
				return new TimeSpanForCalc( this.startDateClock, this.prescribedTimezoneSetting.getMorningEndTime() );
			case PM:
				return new TimeSpanForCalc( this.prescribedTimezoneSetting.getAfternoonStartTime(), this.getEndDateClock() );
		}

		throw new RuntimeException("Out of range. " + atr);
	}

	/**
	 * 1日の勤務時間範囲
	 * Gets the one day span.
	 * @return the one day span
	 */
	public TimeSpanForCalc getOneDaySpan() {
		return this.getWorkableTimeSpan(AmPmAtr.ONE_DAY);
	}

	/**
	 * 午前の勤務時間範囲
	 * Get the half day of AM span.
	 * @return
	 */
	public TimeSpanForCalc getHalfDayOfAmSpan() {
		return this.getWorkableTimeSpan(AmPmAtr.AM);
	}

	/**
	 * 午後の勤務時間範囲
	 * Get the half day of PM span.
	 * @return
	 */
	public TimeSpanForCalc getHalfDayOfPmSpan() {
		return this.getWorkableTimeSpan(AmPmAtr.PM);
	}


	/**
	 * Gets the predetermine end time.
	 *
	 * @return the predetermine end time
	 */
	public int getPredetermineEndTime() {
		return this.startDateClock.minute() + (int) this.rangeTimeDay.minute();
	}


	/**
	 * 2回勤務を使用するか
	 * @return
	 */
	public boolean isUseShiftTwo() {
		return this.prescribedTimezoneSetting.isUseShiftTwo();
	}


	/**
	 * 勤務NOに対応した時間帯を取得する
	 * Gets the time sheet of.
	 * @param workNo the work no
	 * @return the time sheet of
	 */
	public Optional<TimezoneUse> getTimeSheetOf(int workNo) {
		return this.prescribedTimezoneSetting.getMatchWorkNoTimeSheet(workNo);
	}


	/**
	 * 午前午後区分に応じた所定時間帯
	 * @param atr 午前午後区分
	 * @return 所定時間帯リスト
	 */
	public List<TimezoneUse> getTimezoneByAmPmAtr(AmPmAtr atr) {

		List<TimezoneUse> timezones = Collections.emptyList();
		switch( atr ) {
			case ONE_DAY:	// 1日
				timezones = this.prescribedTimezoneSetting.getUseableTimeZone();
				break;
			case AM:		// 午前
				timezones = this.prescribedTimezoneSetting.getUseableTimeZoneInAm();
				break;
			case PM:		// 午後
				timezones = this.prescribedTimezoneSetting.getUseableTimeZoneInPm();
				break;
		}

		return timezones;

	}

	/**
	 * 午前午後区分に応じた所定時間帯(計算時間帯)
	 * @param atr 午前午後区分
	 * @return 所定時間帯リスト(計算時間帯)
	 */
	public List<TimeSpanForCalc> getTimezoneByAmPmAtrForCalc(AmPmAtr atr) {

		return this.getTimezoneByAmPmAtr(atr).stream().map( e -> e.timeSpan() ).collect(Collectors.toList());

	}


	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param workTimeType the work time type
	 * @param oldDomain the old domain
	 */
	public void correctData(ScreenMode screenMode, WorkTimeDivision workTimeType, PredetemineTimeSetting oldDomain) {
		// Tab 1
		this.prescribedTimezoneSetting.correctData(screenMode, workTimeType, oldDomain.getPrescribedTimezoneSetting());
		this.predTime.correctData(screenMode, workTimeType, oldDomain.getPredTime());
		if (screenMode == ScreenMode.SIMPLE) {
			// Simple mode
			this.rangeTimeDay = oldDomain.getRangeTimeDay();
			this.nightShift = oldDomain.isNightShift();
			this.predetermine = oldDomain.isPredetermine();
		}
	}

	/**
	 * Restore default data.
	 *
	 * @param screenMode the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode, WorkTimeDivision workTimeType) {
		// Tab 1
		this.prescribedTimezoneSetting.correctDefaultData(screenMode, workTimeType);
		this.predTime.correctDefaultData(screenMode, workTimeType);
		if (screenMode == ScreenMode.SIMPLE) {
			// Simple mode
			this.rangeTimeDay = new AttendanceTime(TimeWithDayAttr.MINUTES_OF_DAY);
			this.nightShift = false;
			this.predetermine = false;
		}
	}

	/**
 	 * create this Instance
	 * @return new Instance
	 */
	@Override
	public PredetemineTimeSetting clone() {
		PredetemineTimeSetting cloned = new PredetemineTimeSetting();
		try {
			cloned.companyId = this.companyId;
			cloned.rangeTimeDay = new AttendanceTime(this.rangeTimeDay.v());
			cloned.workTimeCode = new WorkTimeCode(this.workTimeCode.v());
			cloned.predTime = this.predTime.clone();
			cloned.nightShift = this.nightShift ? true : false ;
			cloned.prescribedTimezoneSetting = this.prescribedTimezoneSetting.clone();
			cloned.startDateClock = new TimeWithDayAttr(this.startDateClock.valueAsMinutes());
			cloned.predetermine = this.predetermine ? true : false ;
		}
		catch (Exception e){
			throw new RuntimeException("PredetemineTimeSetting clone error.");
		}
		return cloned;
	}

	public void setStartDateClock(int value) {
		this.startDateClock = new TimeWithDayAttr(value);
	}
}
