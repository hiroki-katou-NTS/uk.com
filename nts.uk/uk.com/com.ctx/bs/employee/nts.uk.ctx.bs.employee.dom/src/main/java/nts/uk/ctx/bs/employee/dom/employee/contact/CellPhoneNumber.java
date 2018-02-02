package nts.uk.ctx.bs.employee.dom.employee.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(20)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class CellPhoneNumber extends StringPrimitiveValue<CellPhoneNumber>{

	private static final long serialVersionUID = 1L;

	public CellPhoneNumber(String rawValue) {
		super(rawValue);
	}


}
