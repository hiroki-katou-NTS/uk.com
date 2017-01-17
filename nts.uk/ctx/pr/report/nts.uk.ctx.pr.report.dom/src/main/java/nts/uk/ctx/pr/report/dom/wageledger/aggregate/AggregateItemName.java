/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.aggregate;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class OutputSettingName.
 */
@StringMaxLength(40)
public class AggregateItemName extends StringPrimitiveValue<AggregateItemName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new output setting name.
	 *
	 * @param rawValue the raw value
	 */
	public AggregateItemName(String rawValue) {
		super(rawValue);
	}
}
