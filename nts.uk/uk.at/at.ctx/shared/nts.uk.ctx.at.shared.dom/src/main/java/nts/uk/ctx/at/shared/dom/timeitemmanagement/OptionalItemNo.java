package nts.uk.ctx.at.shared.dom.timeitemmanagement;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * Code of Company
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(4)
public class OptionalItemNo extends CodePrimitiveValue<OptionalItemNo>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public OptionalItemNo(String rawValue) {
		super(rawValue);
	}

}
