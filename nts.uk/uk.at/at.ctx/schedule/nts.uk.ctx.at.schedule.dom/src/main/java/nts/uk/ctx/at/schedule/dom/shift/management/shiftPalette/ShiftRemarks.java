package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author phongtq
 *
 */

@StringMaxLength(200)
public class ShiftRemarks extends StringPrimitiveValue<ShiftRemarks>{

	public ShiftRemarks(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
	
}
