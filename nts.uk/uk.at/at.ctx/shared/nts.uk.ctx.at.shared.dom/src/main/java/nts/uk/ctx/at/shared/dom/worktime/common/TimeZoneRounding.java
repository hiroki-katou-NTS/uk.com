/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;

/**
 * The Class TimeZoneRounding.
 */
//時間帯(丸め付き)
@Getter
public class TimeZoneRounding extends TimeZone {

	/** The rounding. */
	// 丸め
	private TimeRoundingSetting rounding;

	/**
	 * Instantiates a new time zone rounding.
	 *
	 * @param memento the memento
	 */
	public TimeZoneRounding(TimeZoneRoundingGetMemento memento) {
		this.rounding = memento.getRounding();
		this.start = memento.getStart();
		this.end = memento.getEnd();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TimeZoneRoundingSetMemento memento) {
		memento.setRounding(this.rounding);
		memento.setStart(this.start);
		memento.setEnd(this.end);
	}

	/**
	 * Checks if is overlap.
	 *
	 * @param timezone the timezone
	 * @return true, if is overlap
	 */
	public boolean isOverlap(TimeZoneRounding timezone) {
		return !(this.end.lessThan(timezone.getStart()) || this.start.greaterThan(timezone.getEnd()));
	}

	/**
	 * Checks if is between or equal.
	 *
	 * @param timezone the timezone
	 * @return true, if is between or equal
	 */
	public boolean isBetweenOrEqual(TimeZoneRounding timezone) {
		return this.start.greaterThanOrEqualTo(timezone.start) && this.end.lessThanOrEqualTo(timezone.getEnd());
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		
		if (this.start.greaterThanOrEqualTo(this.end)) {
			throw new BusinessException("Msg_770");
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.start.v() + "," + this.end.v();
	}
	
}
