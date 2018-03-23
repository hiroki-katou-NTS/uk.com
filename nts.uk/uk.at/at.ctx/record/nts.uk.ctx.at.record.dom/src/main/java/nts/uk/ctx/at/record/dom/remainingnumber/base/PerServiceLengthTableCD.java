package nts.uk.ctx.at.record.dom.remainingnumber.base;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(2)
@StringCharType(CharType.ALPHA_NUMERIC)
public class PerServiceLengthTableCD extends CodePrimitiveValue<PerServiceLengthTableCD>{

	private static final long serialVersionUID = 1L;

	public PerServiceLengthTableCD(String rawValue) {
		super(rawValue);
	}

}
