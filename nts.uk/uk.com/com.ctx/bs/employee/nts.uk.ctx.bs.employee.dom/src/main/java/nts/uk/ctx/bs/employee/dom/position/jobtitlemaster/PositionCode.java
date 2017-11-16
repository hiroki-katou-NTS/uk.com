package nts.uk.ctx.bs.employee.dom.position.jobtitlemaster;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;


@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(5)
public class PositionCode extends StringPrimitiveValue<PositionCode>{

	public PositionCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
