package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * シフト組み合わせ名称
 * @author phongtq
 *
 */

@StringMaxLength(12)
public class ShiftCombinationName extends StringPrimitiveValue<ShiftCombinationName>{

	public ShiftCombinationName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
