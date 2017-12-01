/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

// 所定時間設定
@Getter
public class PredetemineTimeSet extends AggregateRoot {

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

	/** The night shift atr. */
	// 夜勤区分
	private boolean nightShift;

	/** The prescribed timezone setting. */
	// 所定時間帯
	private PrescribedTimezoneSetting prescribedTimezoneSetting;

	/** The start date clock. */
	// 日付開始時刻
	private TimeWithDayAttr startDateClock;

	/** The predetermine atr. */
	// 残業を含めた所定時間帯を設定する
	private boolean predetermine;

	private static final Integer SHIFT1 = 1;
	private static final Integer SHIFT2 = 2;

	/**
	 * Instantiates a new predetemine time set.
	 *
	 * @param memento
	 *            the memento
	 */
	public PredetemineTimeSet(PredetemineTimeGetMemento memento) {
		this.companyId = memento.getCompanyID();
		this.rangeTimeDay = memento.getRangeTimeDay();
		this.workTimeCode = memento.getWorkTimeCode();
		this.predTime = memento.getPredTime();
		this.nightShift = memento.isNightShift();
		this.prescribedTimezoneSetting = memento.getPrescribedTimezoneSetting();
		this.startDateClock = memento.getStartDateClock();
		this.predetermine = memento.isPredetermine();
	}

	public void saveToMemento(PredetemineTimeSetMemento memento) {
		memento.setCompanyID(this.companyId);
		memento.setRangeTimeDay(this.rangeTimeDay);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setPredTime(this.predTime);
		memento.setNightShift(this.nightShift);
		memento.setPrescribedTimezoneSetting(this.prescribedTimezoneSetting);
		memento.setStartDateClock(this.startDateClock);
		memento.setPredetermine(this.predetermine);
	}

	/**
	 * Update start time shift 1.
	 *
	 * @param start
	 *            the start
	 */
	public void updateStartTimeShift1(TimeWithDayAttr start) {
		Timezone tz = this.prescribedTimezoneSetting.getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT1).findFirst().get();
		tz.updateStartTime(start);
	}

	/**
	 * Update end time shift 1.
	 *
	 * @param end
	 *            the end
	 */
	public void updateEndTimeShift1(TimeWithDayAttr end) {
		Timezone tz = this.prescribedTimezoneSetting.getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT1).findFirst().get();
		tz.updateEndTime(end);
	}

	/**
	 * Removes the shift 1.
	 */
	public void removeShift1() {
		Timezone tz = this.prescribedTimezoneSetting.getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT1).findFirst().get();
		tz.updateStartTime(null);
		tz.updateEndTime(null);
	}

	/**
	 * Removes the shift 2.
	 */
	public void removeShift2() {
		Timezone tz = this.prescribedTimezoneSetting.getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT2).findFirst().get();
		tz.updateStartTime(null);
		tz.updateEndTime(null);
	}

	/**
	 * Update start time shift 2.
	 *
	 * @param start
	 *            the start
	 */
	public void updateStartTimeShift2(TimeWithDayAttr start) {
		Timezone tz = this.prescribedTimezoneSetting.getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT2).findFirst().get();
		tz.updateStartTime(start);
	}

	/**
	 * Update end time shift 2.
	 *
	 * @param end
	 *            the end
	 */
	public void updateEndTimeShift2(TimeWithDayAttr end) {
		Timezone tz = this.prescribedTimezoneSetting.getTimezone().stream()
				.filter(timezone -> timezone.getWorkNo() == SHIFT2).findFirst().get();
		tz.updateEndTime(end);
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
		if (!(obj instanceof PredetemineTimeSet))
			return false;
		PredetemineTimeSet other = (PredetemineTimeSet) obj;
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

}
