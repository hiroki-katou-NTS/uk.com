/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AmountRange.
 */
// 金額範囲
@Getter
public class AmountRange extends DomainObject{

	/** The upper limit. */
	// 上限値
	private AmountRangeValue upperLimit;

	/** The lower limit. */
	// 下限値
	private AmountRangeValue lowerLimit;
}
