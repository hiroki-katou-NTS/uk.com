package nts.uk.ctx.hr.shared.dom.primitiveValue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class String_Any_100 extends StringPrimitiveValue<String_Any_100> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public String_Any_100 (String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
