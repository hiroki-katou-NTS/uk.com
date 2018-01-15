/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.frame;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class OvertimeWorkFrameName.
 */
//残業枠名称
@StringMaxLength(12)
public class OvertimeWorkFrameName extends StringPrimitiveValue<OvertimeWorkFrameName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3072948248326997648L;

	/**
	 * Instantiates a new overtime work frame name.
	 *
	 * @param rawValue the raw value
	 */
	public OvertimeWorkFrameName(String rawValue) {
		super(rawValue);
	}
	
}
