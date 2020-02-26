package nts.uk.ctx.hr.notice.dom.report;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
@ZeroPaddedCode
public class Code_AlphaNumeric_3 extends StringPrimitiveValue<Code_AlphaNumeric_3> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public Code_AlphaNumeric_3 (String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}


}
