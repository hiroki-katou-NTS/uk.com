/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset;

import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PredetemineTimeSetting.
 */
// 所定時間設定
@Getter
public class PredetemineTimeSetting extends AggregateRoot {

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

	/**
	 * Instantiates a new predetemine time set.
	 *
	 * @param memento
	 *            the memento
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

	@Override
	public void validate() {
		super.validate();
		
		// validate startDateClock in -12:00 ~ 23:59
		if ((this.startDateClock.valueAsMinutes() < TimeWithDayAttr.THE_PREVIOUS_DAY_1200.valueAsMinutes())
				|| (this.startDateClock.valueAsMinutes() >= TimeWithDayAttr.THE_NEXT_DAY_0000.valueAsMinutes())) {
			throw new BusinessException("Msg_785");
		}
		
		// validate timezone
		int startTime = this.startDateClock.valueAsMinutes();
		int endTime = this.startDateClock.valueAsMinutes() + this.rangeTimeDay.valueAsMinutes();
		boolean isInValidTimezone = this.prescribedTimezoneSetting.getLstTimezone().stream()
				.filter(timezone -> (timezone.getStart().valueAsMinutes() < startTime)
						|| (timezone.getEnd().valueAsMinutes() > endTime))
				.collect(Collectors.toList()).isEmpty();

		// validate morning End time and afternoon start time
		int morningEnd = this.prescribedTimezoneSetting.getMorningEndTime().valueAsMinutes();
		int afternoonStart = this.prescribedTimezoneSetting.getAfternoonStartTime().valueAsMinutes();
		boolean isInvalidStartEnd = (morningEnd < startTime) || (afternoonStart > endTime);
		
		if (isInValidTimezone || isInvalidStartEnd) {
			throw new BusinessException("Msg_516");
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

}
