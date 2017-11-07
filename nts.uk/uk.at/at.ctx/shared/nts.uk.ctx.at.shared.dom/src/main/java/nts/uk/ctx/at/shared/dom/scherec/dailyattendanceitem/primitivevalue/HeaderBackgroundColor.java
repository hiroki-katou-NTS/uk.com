package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(7)
public class HeaderBackgroundColor extends StringPrimitiveValue<HeaderBackgroundColor> {

	private static final long serialVersionUID = 1L;

	public HeaderBackgroundColor(String rawValue) {
		super(rawValue);
	}
}
