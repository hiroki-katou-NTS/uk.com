/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class OvertimeName.
 */
// 時間外超過の超過時間名称
@StringMaxLength(10)
public class OvertimeName extends StringPrimitiveValue<OvertimeName>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new overtime name.
	 *
	 * @param rawValue the raw value
	 */
	public OvertimeName(String rawValue) {
		super(rawValue);
	}


}
