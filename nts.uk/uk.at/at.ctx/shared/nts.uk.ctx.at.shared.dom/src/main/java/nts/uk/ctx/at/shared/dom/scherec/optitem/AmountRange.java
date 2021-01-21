/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AmountRange.
 */
// 金額範囲
// 事前条件 : 上限値≧下限値
@Getter
public class AmountRange extends DomainObject {

	/** The upper limit. */
	// 上限値
	private Optional<AmountRangeValue> upperLimit;

	/** The lower limit. */
	// 下限値
	private Optional<AmountRangeValue> lowerLimit;

	/**
	 * Instantiates a new amount range.
	 *
	 * @param upperLimit the upper limit
	 * @param lowerLimit the lower limit
	 */
	public AmountRange(Integer upperLimit, Integer lowerLimit) {
		super();
		if (upperLimit == null) {
			this.upperLimit = Optional.empty();
		} else {
			this.upperLimit = Optional.of(new AmountRangeValue(upperLimit));
		}
		if (lowerLimit == null) {
			this.lowerLimit = Optional.empty();
		} else {
			this.lowerLimit = Optional.of(new AmountRangeValue(lowerLimit));
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
