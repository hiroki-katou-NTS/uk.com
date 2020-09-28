package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * パターン名
 */
@StringMaxLength(40)
public class PatternName extends StringPrimitiveValue<PatternName> {
	
	private static final long serialVersionUID = 1L;

	public PatternName(String rawValue) {
		super(rawValue);
	}
	
}
