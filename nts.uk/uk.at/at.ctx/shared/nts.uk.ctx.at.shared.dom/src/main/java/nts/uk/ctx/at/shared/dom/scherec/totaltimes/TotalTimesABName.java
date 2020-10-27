/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.totaltimes;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 回数集計略名
 */
@StringMaxLength(4)
public class TotalTimesABName extends StringPrimitiveValue<TotalTimesABName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2960364556648891076L;

	/**
	 * Instantiates a new priority.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public TotalTimesABName(String rawValue) {
		super(rawValue);
	}

}
