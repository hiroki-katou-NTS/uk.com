package nts.uk.ctx.at.shared.dom.era.name;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(1)
public class SymbolName extends StringPrimitiveValue<SymbolName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SymbolName(String rawValue) {
		super(rawValue);
	}

}
