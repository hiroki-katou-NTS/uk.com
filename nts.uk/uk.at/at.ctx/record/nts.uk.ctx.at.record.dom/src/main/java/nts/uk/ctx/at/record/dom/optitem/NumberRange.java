/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.error.BusinessException;
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
	private NumberRangeValue upperLimit;

	/** The lower limit. */
	// 下限値
	private NumberRangeValue lowerLimit;

	/**
	 * Instantiates a new number range.
	 *
	 * @param upperLimit the upper limit
	 * @param lowerLimit the lower limit
	 */
	public NumberRange(BigDecimal upperLimit, BigDecimal lowerLimit) {
		super();
		this.upperLimit = new NumberRangeValue(upperLimit);
		this.lowerLimit = new NumberRangeValue(lowerLimit);
	}

	/**
	 * Validate range.
	 */
	public void validateRange() {
		// Validate
		if (this.isInvalidRange()) {
			throw new BusinessException("Msg_574");
		}
	}

	/**
	 * Checks if is invalid range.
	 *
	 * @return true, if is invalid range
	 */
	private boolean isInvalidRange() {
		this.validate();
		if (this.upperLimit.lessThan(this.lowerLimit)) {
			return true;
		}
		return false;
	}

}
