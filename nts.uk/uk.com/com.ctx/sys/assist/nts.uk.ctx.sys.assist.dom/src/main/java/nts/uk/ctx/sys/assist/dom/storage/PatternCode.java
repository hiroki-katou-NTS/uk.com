package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * パターンコード
 */
@StringMaxLength(4)
@ZeroPaddedCode
public class PatternCode extends UpperCaseAlphaNumericPrimitiveValue<PatternCode> {
	
	private static final long serialVersionUID = 1L;

	public PatternCode(String rawValue) {
		super(rawValue);
	}
}
