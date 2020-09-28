package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * パターンコード
 */
@StringMaxLength(4)
public class PatternCode extends UpperCaseAlphaNumericPrimitiveValue<PatternCode> {
	
	private static final long serialVersionUID = 1L;

	public PatternCode(String rawValue) {
		super(rawValue);
	}
}
