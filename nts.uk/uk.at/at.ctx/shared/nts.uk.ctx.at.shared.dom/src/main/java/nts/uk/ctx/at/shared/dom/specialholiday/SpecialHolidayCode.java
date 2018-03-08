package nts.uk.ctx.at.shared.dom.specialholiday;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringRegEx("^0*([1-9]|1[0-9]|20)$")
//@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class SpecialHolidayCode extends CodePrimitiveValue<SpecialHolidayCode> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public SpecialHolidayCode(String rawValue) {
		super(rawValue);
	}
}
