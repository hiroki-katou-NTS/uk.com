/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.amountrounding;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AmountRounding.
 */
// 金額丸め
@Getter
public class AmountRounding extends DomainObject{
	
	/** The unit. */
	// 単位
	private Unit roundingTime;
	
	/** The rounding. */
	// 端数処理
	private Rounding rounding;

}
