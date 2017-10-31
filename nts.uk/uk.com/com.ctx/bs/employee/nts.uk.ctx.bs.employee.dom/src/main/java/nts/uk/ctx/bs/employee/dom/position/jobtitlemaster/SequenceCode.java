package nts.uk.ctx.bs.employee.dom.position.jobtitlemaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class SequenceCode extends StringPrimitiveValue<SequenceCode>{

	public SequenceCode(String rawValue) {
		super(rawValue);
	}

}
