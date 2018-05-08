/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class OTFrameNo.
 */
// 残業枠NO
@IntegerRange(min = 1, max = 10)
public class OTFrameNo extends IntegerPrimitiveValue<OTFrameNo> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new OT frame no.
	 *
	 * @param rawValue the raw value
	 */
	public OTFrameNo(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * Gets the default data.
	 *
	 * @return the default data
	 */
	public static OTFrameNo getDefaultData() {
		return new OTFrameNo(1);
	}

}
