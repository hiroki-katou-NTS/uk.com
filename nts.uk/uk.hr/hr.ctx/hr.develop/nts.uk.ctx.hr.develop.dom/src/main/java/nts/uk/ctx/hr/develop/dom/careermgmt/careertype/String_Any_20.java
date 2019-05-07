package nts.uk.ctx.hr.develop.dom.careermgmt.careertype;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class String_Any_20 extends StringPrimitiveValue<String_Any_20> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public String_Any_20 (String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
