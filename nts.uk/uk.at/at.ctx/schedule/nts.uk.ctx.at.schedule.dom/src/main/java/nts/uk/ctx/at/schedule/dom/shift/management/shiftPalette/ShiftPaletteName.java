package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * シフトパレット名称
 * @author phongtq
 *
 */

@StringMaxLength(10)
public class ShiftPaletteName extends StringPrimitiveValue<ShiftPaletteName>{

	public ShiftPaletteName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
