package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(10)
public class BusinessTypeCode  extends CodePrimitiveValue<BusinessTypeCode>{

	private static final long serialVersionUID = 1L;
	
	public BusinessTypeCode(String rawValue) {
		super(rawValue);
	}

}
