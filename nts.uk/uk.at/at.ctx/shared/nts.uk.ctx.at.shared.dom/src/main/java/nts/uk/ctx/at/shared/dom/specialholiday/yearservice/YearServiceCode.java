package nts.uk.ctx.at.shared.dom.specialholiday.yearservice;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class YearServiceCode extends CodePrimitiveValue<YearServiceCode> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public YearServiceCode(String rawValue) {
		super(rawValue);
	}
}
