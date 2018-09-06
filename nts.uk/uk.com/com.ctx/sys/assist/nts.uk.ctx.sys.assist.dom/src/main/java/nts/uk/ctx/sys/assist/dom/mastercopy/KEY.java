package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

//KEY
@StringMaxLength(30)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class KEY extends StringPrimitiveValue<KEY> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KEY(String rawValue) {
		super(rawValue);
	}

}
