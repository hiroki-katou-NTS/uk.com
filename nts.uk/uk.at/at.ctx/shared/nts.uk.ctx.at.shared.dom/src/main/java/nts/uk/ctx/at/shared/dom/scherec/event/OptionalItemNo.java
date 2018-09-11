/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.event;

import nts.arc.primitive.IntegerPrimitiveValue;

/**
 * The Class OptionalItemNo.
 */
// 任意項目NO
// <<不変条件>> 001~100まで固定
public class OptionalItemNo extends IntegerPrimitiveValue<OptionalItemNo>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new optional item no.
	 *
	 * @param rawValue the raw value
	 */
	public OptionalItemNo(Integer rawValue) {
		super(rawValue);
	}

}
