/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
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
	private AmountRangeValue upperLimit;

	/** The lower limit. */
	// 下限値
	private AmountRangeValue lowerLimit;

	/**
	 * Instantiates a new amount range.
	 *
	 * @param upperLimit the upper limit
	 * @param lowerLimit the lower limit
	 */
	public AmountRange(int upperLimit, int lowerLimit) {
		super();
		this.upperLimit = new AmountRangeValue(upperLimit);
		this.lowerLimit = new AmountRangeValue(lowerLimit);

		// Validate
		if (isInvalidRange()) {
			throw new BusinessException(new RawErrorMessage("Upper limit >= Lower limit"));
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
