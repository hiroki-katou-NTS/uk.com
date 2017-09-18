/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.numberrounding;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class NumberRounding.
 */
// 数値丸め
@Getter
public class NumberRounding extends DomainObject{
	
	/** The unit. */
	// 単位
	private Unit unit;
	
	/** The rounding. */
	// 端数処理
	private Rounding rounding;

	/**
	 * Instantiates a new number rounding.
	 *
	 * @param unit the unit
	 * @param rounding the rounding
	 */
	public NumberRounding(Unit unit, Rounding rounding) {
		super();
		this.unit = unit;
		this.rounding = rounding;
	}

}
