/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeZone.
 */
// 時間帯
@Getter
public class TimeZone extends DomainObject {

	/** The start. */
	// 開始
	protected TimeWithDayAttr start;

	/** The end. */
	// 終了
	protected TimeWithDayAttr end;

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

	/**
	 * Checks if is overlap.
	 *
	 * @param timezone the timezone
	 * @return true, if is overlap
	 */
	public boolean isOverlap(TimeZone timezone) {
		return !(this.end.lessThan(timezone.getStart()) || this.start.greaterThan(timezone.getEnd()));
	}

	/**
	 * Checks if is start less than.
	 *
	 * @param timezone the timezone
	 * @return true, if is start less than
	 */
	public boolean isStartLessThan(TimeZone timezone) {
		return this.start.lessThan(timezone.getStart());
	}

	/**
	 * Checks if is between or equal.
	 *
	 * @param timezone the timezone
	 * @return true, if is between or equal
	 */
	public boolean isBetweenOrEqual(TimeZone timezone) {
		return this.start.greaterThanOrEqualTo(timezone.start) && this.end.lessThanOrEqualTo(timezone.getEnd());
	}
}
