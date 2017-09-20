/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AmountRange.
 */
// 金額範囲
// 事前条件 : 上限値≧下限値
@Getter
public class NumberRange extends DomainObject{

	/** The upper limit. */
	// 上限値
	private NumberRangeValue upperLimit;

	/** The lower limit. */
	// 下限値
	private NumberRangeValue lowerLimit;

	/**
	 * Validate range.
	 */
	public void validateRange() {
		if(this.upperLimit.lessThan(this.lowerLimit)) {
			throw new BusinessException("Upper limit >= Lower limit");
		}
	}

}
