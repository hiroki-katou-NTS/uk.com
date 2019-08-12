package nts.uk.ctx.hr.shared.dom.primitiveValue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(500)
public class String_Any_500 extends StringPrimitiveValue<String_Any_500> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public String_Any_500 (String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
