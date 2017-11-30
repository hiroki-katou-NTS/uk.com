/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.time.TimeWithDayAttr;

// 所定時間設定
@Getter
public class PredetemineTimeSet extends AggregateRoot {

	/** The company ID. */
	// 会社ID
	private String companyID;

	/** The range time day. */
	// １日の範囲時間
	private int rangeTimeDay;

	/** The sift CD. */
	// コード
	private String siftCD;

	/** The addition set ID. */
	// 所定時間
	private String additionSetID;

	/** The night shift atr. */
	// 夜勤区分
	private boolean nightShift;

	/** The prescribed timezone setting. */
	// 所定時間帯
	private PrescribedTimezoneSetting prescribedTimezoneSetting;

	/** The start date clock. */
	// 日付開始時刻
	private int startDateClock;

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
		this.companyID = memento.getCompanyID();
		this.rangeTimeDay = memento.getRangeTimeDay();
		this.siftCD = memento.getSiftCD();
		this.additionSetID = memento.getAdditionSetID();
		this.nightShift = memento.isNightShift();
		this.prescribedTimezoneSetting = memento.getPrescribedTimezoneSetting();
		this.startDateClock = memento.getStartDateClock();
		this.predetermine = memento.isPredetermine();
	}

	public void saveToMemento(PredetemineTimeSetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setRangeTimeDay(this.rangeTimeDay);
		memento.setSiftCD(this.siftCD);
		memento.setAdditionSetID(this.additionSetID);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
		result = prime * result + ((siftCD == null) ? 0 : siftCD.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PredetemineTimeSet other = (PredetemineTimeSet) obj;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		if (siftCD == null) {
			if (other.siftCD != null)
				return false;
		} else if (!siftCD.equals(other.siftCD))
			return false;
		return true;
	}

}
