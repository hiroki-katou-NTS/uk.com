package nts.uk.ctx.hr.shared.dom.primitiveValue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class String_Any_Half_Width_2 extends StringPrimitiveValue<String_Any_Half_Width_2> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public String_Any_Half_Width_2 (String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
