package nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class OutItemsWoScCode extends CodePrimitiveValue<OutItemsWoScCode>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutItemsWoScCode(String rawValue) {
		super(rawValue);
	}
}
