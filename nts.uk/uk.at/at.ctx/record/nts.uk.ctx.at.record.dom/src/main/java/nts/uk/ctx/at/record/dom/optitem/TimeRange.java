/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class TimeRange.
 */
// 時間範囲
// 事前条件 : 上限値≧下限値
@Getter
public class TimeRange extends DomainObject {

	/** The upper limit. */
	// 上限値
	private TimeRangeValue upperLimit;

	/** The lower limit. */
	// 下限値
	private TimeRangeValue lowerLimit;

	/**
	 * Instantiates a new time range.
	 *
	 * @param upperLimit the upper limit
	 * @param lowerLimit the lower limit
	 */
	public TimeRange(int upperLimit, int lowerLimit) {
		super();
		this.upperLimit = new TimeRangeValue(upperLimit);
		this.lowerLimit = new TimeRangeValue(lowerLimit);
	}

	/**
	 * Validate range.
	 */
	public void validateRange() {
		if (isInvalidRange()) {
			throw new BusinessException("Msg_574");
		}
	}

	/**
	 * Checks if is invalid range.
	 *
	 * @return true, if is invalid range
	 */
	private boolean isInvalidRange() {
		if (this.upperLimit.lessThan(this.lowerLimit)) {
			return true;
		}
		return false;
	}

}
