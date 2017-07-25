package nts.uk.ctx.at.shared.dom.attendanceitem.primitives;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(7)
public class HeaderBackgroundColor extends CodePrimitiveValue<HeaderBackgroundColor> {

	private static final long serialVersionUID = 1L;

	public HeaderBackgroundColor(String rawValue) {
		super(rawValue);
	}
}
