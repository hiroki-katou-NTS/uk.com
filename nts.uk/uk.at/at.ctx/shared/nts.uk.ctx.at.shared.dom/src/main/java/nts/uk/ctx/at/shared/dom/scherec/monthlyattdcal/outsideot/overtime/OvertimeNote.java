/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class OvertimeNote.
 */
// 時間外超過の備考
@StringMaxLength(500)
public class OvertimeNote extends StringPrimitiveValue<OvertimeNote>{


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2474151010976326630L;

	/**
	 * Instantiates a new overtime note.
	 *
	 * @param rawValue the raw value
	 */
	public OvertimeNote(String rawValue) {
		super(rawValue);
	}
}
