package nts.uk.ctx.at.function.dom.scheduletable;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class OutputSettingCode extends CodePrimitiveValue<OutputSettingCode>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6832522276775724826L;

	public OutputSettingCode(String rawValue) {
		super(rawValue);
	}

}
