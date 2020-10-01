/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class NumberRange.
 */
// 金額範囲
// 事前条件 : 上限値≧下限値

@Getter
public class NumberRange extends DomainObject {

	/** The upper limit. */
	// 上限値
	private Optional<NumberRangeValue> upperLimit;

	/** The lower limit. */
	// 下限値
	private Optional<NumberRangeValue> lowerLimit;

	/**
	 * Instantiates a new number range.
	 *
	 * @param upperLimit the upper limit
	 * @param lowerLimit the lower limit
	 */
	public NumberRange(Double upperLimit, Double lowerLimit) {
		super();
		if (upperLimit == null) {
			this.upperLimit = Optional.empty();
		} else {
			this.upperLimit = Optional.of(new NumberRangeValue(upperLimit));
		}
		if (lowerLimit == null) {
			this.lowerLimit = Optional.empty();
		} else {
			this.lowerLimit = Optional.of(new NumberRangeValue(lowerLimit));
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
