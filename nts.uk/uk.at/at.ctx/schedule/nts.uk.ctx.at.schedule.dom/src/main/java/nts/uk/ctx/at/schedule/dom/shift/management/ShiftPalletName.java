package nts.uk.ctx.at.schedule.dom.shift.management;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * シフトパレット名称
 * @author phongtq
 *
 */

@StringMaxLength(10)
public class ShiftPalletName extends StringPrimitiveValue<ShiftPalletName>{

	public ShiftPalletName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
