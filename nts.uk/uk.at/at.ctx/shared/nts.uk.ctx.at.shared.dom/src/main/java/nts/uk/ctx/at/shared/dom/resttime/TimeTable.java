/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.resttime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;

/**
 * The Class TimeTable.
 */
@Getter
public class TimeTable {

	/** The start day. */
	private TimeDayAtr startDay;

	/** The start time. */
	private Integer startTime;

	/** The end day. */
	private TimeDayAtr endDay;

	/** The end time. */
	private Integer endTime;

	
	/**
	 * Instantiates a new time table.
	 *
	 * @param memento the memento
	 */
	public TimeTable(TimeTableGetMemento memento) {
		this.startDay = memento.getStartDay();
		this.startTime = memento.getStartTime();
		this.endDay = memento.getEndDay();
		this.endTime = memento.getEndTime();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TimeTableSetMemento memento) {
		memento.setStartDay(this.startDay);
		memento.setStartTime(this.startTime);
		memento.setEndDay(this.endDay);
		memento.setEndTime(this.endTime);
	}

	/**
	 * @param startDay
	 * @param startTime
	 * @param endDay
	 * @param endTime
	 */
	public TimeTable(TimeDayAtr startDay, Integer startTime, TimeDayAtr endDay, Integer endTime) {
		this.startDay = startDay;
		this.startTime = startTime;
		this.endDay = endDay;
		this.endTime = endTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDay == null) ? 0 : endDay.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((startDay == null) ? 0 : startDay.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		if (!(obj instanceof TimeTable))
			return false;
		TimeTable other = (TimeTable) obj;
		if (endDay != other.endDay)
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startDay != other.startDay)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	
	
}
