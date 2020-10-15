/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

import lombok.Getter;
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
	private Optional<TimeRangeValue> upperLimit;

	/** The lower limit. */
	// 下限値
	private Optional<TimeRangeValue> lowerLimit;

	/**
	 * Instantiates a new time range.
	 *
	 * @param upperLimit the upper limit
	 * @param lowerLimit the lower limit
	 */
	public TimeRange(Integer upperLimit, Integer lowerLimit) {
		super();
		if (upperLimit == null) {
			this.upperLimit = Optional.empty();
		} else {
			this.upperLimit = Optional.of(new TimeRangeValue(upperLimit));
		}
		if (lowerLimit == null) {
			this.lowerLimit = Optional.empty();
		} else {
			this.lowerLimit = Optional.of(new TimeRangeValue(lowerLimit));
		}
	}

	/**
	 * Checks if is invalid range.
	 *
	 * @return true, if is invalid range
	 */
	public boolean isInvalidRange() {
		if (this.lowerLimit.get().greaterThan(this.upperLimit.get())) {
			return true;
		}
		return false;
	}

}
