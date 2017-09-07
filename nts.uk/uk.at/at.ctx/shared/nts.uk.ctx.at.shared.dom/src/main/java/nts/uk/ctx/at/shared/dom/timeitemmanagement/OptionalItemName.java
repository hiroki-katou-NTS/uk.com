package nts.uk.ctx.at.shared.dom.timeitemmanagement;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;

/**
 * Name of Company
 */
@StringCharType(CharType.ALPHA_NUMERIC)
public class OptionalItemName extends StringPrimitiveValue<OptionalItemName>{
	/**	 * serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	public OptionalItemName(String rawValue) {
		super(rawValue);
	}


}
