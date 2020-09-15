package nts.uk.ctx.at.function.dom.processexecution;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 補助パターンコード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(4)
@ZeroPaddedCode
public class AuxiliaryPatternCode extends CodePrimitiveValue<AuxiliaryPatternCode> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public AuxiliaryPatternCode(String rawValue) {
		super(rawValue);
	}
}
