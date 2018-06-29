package nts.uk.ctx.at.shared.dom.era.name;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class SymbolName.
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(1)
public class SymbolName extends StringPrimitiveValue<SymbolName> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new symbol name.
	 *
	 * @param rawValue the raw value
	 */
	public SymbolName(String rawValue) {
		super(rawValue);
	}

}
