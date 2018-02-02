/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeZone.
 */
// 時間帯
@Getter
public class TimeZone extends WorkTimeDomainObject {

	/** The start. */
	// 開始
	protected TimeWithDayAttr start;

	/** The end. */
	// 終了
	protected TimeWithDayAttr end;

	
	/**
	 * Instantiates a new time zone.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public TimeZone(TimeWithDayAttr start, TimeWithDayAttr end) {
		super();
		this.start = start;
		this.end = end;
	}

	/**
	 * Instantiates a new time zone.
	 */
	public TimeZone() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	public void validateRange(String param) {
		if (this.start.greaterThanOrEqualTo(this.end)) {
			this.bundledBusinessExceptions.addMessage("Msg_770", param);
		}
	}

	/**
	 * Checks if is overlap.
	 *
	 * @param timezone
	 *            the timezone
	 * @return true, if is overlap
	 */
	public boolean isOverlap(TimeZone timezone) {
		return !(this.end.lessThanOrEqualTo(timezone.getStart())
				|| this.start.greaterThanOrEqualTo(timezone.getEnd()));
	}
	
	/**
	 * Checks if is start less than.
	 *
	 * @param timezone
	 *            the timezone
	 * @return true, if is start less than
	 */
	public boolean isStartLessThan(TimeZone timezone) {
		return this.start.lessThan(timezone.getStart());
	}

	/**
	 * Checks if is between or equal.
	 *
	 * @param timezone
	 *            the timezone
	 * @return true, if is between or equal
	 */
	public boolean isBetweenOrEqual(TimeZone timezone) {
		return this.start.greaterThanOrEqualTo(timezone.getStart())
				&& this.end.lessThanOrEqualTo(timezone.getEnd());
	}

	/**
	 * Consist of.
	 *
	 * @param time
	 *            the time
	 * @return true, if successful
	 */
	public boolean consistOf(TimeWithDayAttr time) {
		return time.greaterThanOrEqualTo(this.start) && time.lessThanOrEqualTo(this.end);
	}
	
	/**
	 * Convert Time Span
	 * @return timeSpanForCalc
	 */
	public TimeSpanForCalc timeSpan() {
		return new TimeSpanForCalc(this.start,this.end);
	}

}
