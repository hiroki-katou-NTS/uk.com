package nts.uk.ctx.at.shared.dom.worktimeset_old;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.commonsetting.PredetermineTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

// 所定時間設定
@Getter
public class WorkTimeSet extends AggregateRoot {

	/** The company ID. */
	// 会社ID
	private String companyID;

	/** The range time day. */
	// １日の範囲時間
	private AttendanceTime rangeTimeDay;

	/** The sift CD. */
	// コード
	private String siftCD;

	/** The addition set ID. */
	// 所定時間
	private PredetermineTime additionSetID;

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

	/** The shift one. */
	public static Integer SHIFT1 = 1;

	/** The shift two. */
	public static Integer SHIFT2 = 2;

	/**
	 * Instantiates a new work time set.
	 *
	 * @param companyID
	 *            the company ID
	 * @param rangeTimeDay
	 *            the range time day
	 * @param siftCD
	 *            the sift CD
	 * @param additionSetID
	 *            the addition set ID
	 * @param nightShift
	 *            the night shift
	 * @param prescribedTimezoneSetting
	 *            the prescribed timezone setting
	 * @param startDateClock
	 *            the start date clock
	 * @param predetermine
	 *            the predetermine
	 */
	public WorkTimeSet(String companyID, AttendanceTime rangeTimeDay, String siftCD, PredetermineTime additionSetID,
			boolean nightShift, PrescribedTimezoneSetting prescribedTimezoneSetting, TimeWithDayAttr startDateClock,
			boolean predetermine) {
		super();
		this.companyID = companyID;
		this.rangeTimeDay = rangeTimeDay;
		this.siftCD = siftCD;
		this.additionSetID = additionSetID;
		this.nightShift = nightShift;
		this.prescribedTimezoneSetting = prescribedTimezoneSetting;
		this.startDateClock = startDateClock;
		this.predetermine = predetermine;
	}

	/**
	 * Update start time shift 1.
	 *
	 * @param start
	 *            the start
	 */
	public void updateStartTimeShift1(TimeWithDayAttr start) {
		Timezone tz = getTimezoneShiftOne();
		tz.updateStartTime(start);
	}

	/**
	 * Update end time shift 1.
	 *
	 * @param end
	 *            the end
	 */
	public void updateEndTimeShift1(TimeWithDayAttr end) {
		Timezone tz = getTimezoneShiftOne();
		tz.updateEndTime(end);
	}

	/**
	 * Removes the shift 1.
	 */
	public void removeShift1() {
		Timezone tz = getTimezoneShiftOne();
		tz.updateStartTime(null);
		tz.updateEndTime(null);
	}

	/**
	 * Removes the shift 2.
	 */
	public void removeShift2() {
		Timezone tz = getTimezoneShiftTwo();
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
		Timezone tz = getTimezoneShiftTwo();
		tz.updateStartTime(start);
	}

	/**
	 * 1日の範囲を時間帯として返す
	 * 
	 * @return 1日の範囲(時間帯)
	 */
	public TimeSpanForCalc getOneDaySpan() {
		return new TimeSpanForCalc(startDateClock,
				new TimeWithDayAttr(startDateClock.valueAsMinutes() + rangeTimeDay.valueAsMinutes()));
	}

	/*
	 * Update end time shift 2.
	 *
	 * @param end the end
	 */
	public void updateEndTimeShift2(TimeWithDayAttr end) {
		Timezone tz = getTimezoneShiftTwo();
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
		WorkTimeSet other = (WorkTimeSet) obj;
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

	public Timezone getTimeSheetOf(int workNo) {
		return this.getPrescribedTimezoneSetting().getMatchWorkNoTimeSheet(workNo);
	}

	private Timezone getTimezoneShiftOne() {
		return this.prescribedTimezoneSetting.getMatchWorkNoTimeSheet(SHIFT1);
	}

	private Timezone getTimezoneShiftTwo() {
		return this.prescribedTimezoneSetting.getMatchWorkNoTimeSheet(SHIFT2);
	}
}
