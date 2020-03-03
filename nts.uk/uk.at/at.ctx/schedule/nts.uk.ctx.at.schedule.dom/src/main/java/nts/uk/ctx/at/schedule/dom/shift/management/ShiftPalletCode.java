package nts.uk.ctx.at.schedule.dom.shift.management;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * シフトコード 
 * @author phongtq
 *
 */

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
@ZeroPaddedCode
public class ShiftPalletCode  extends CodePrimitiveValue<ShiftPalletCode> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public ShiftPalletCode(String rawValue) {
		super(rawValue);
	}
}

