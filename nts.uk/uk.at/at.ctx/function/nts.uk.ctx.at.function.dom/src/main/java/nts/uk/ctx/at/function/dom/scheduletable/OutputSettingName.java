package nts.uk.ctx.at.function.dom.scheduletable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class OutputSettingName extends StringPrimitiveValue<OutputSettingName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1986792603396898433L;

	public OutputSettingName(String rawValue) {
		super(rawValue);
	}

}
