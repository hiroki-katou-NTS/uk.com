package nts.uk.ctx.at.shared.dom.workrule.businesstype;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author nampt
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(10)
@ZeroPaddedCode
public class BusinessTypeCode extends StringPrimitiveValue<PrimitiveValue<String>>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public BusinessTypeCode(String rawValue) {
		super(rawValue);
	}

}
