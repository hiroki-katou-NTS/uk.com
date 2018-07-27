package nts.uk.ctx.at.function.dom.holidaysremaining;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * コード変換コード
 *
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class HolidayRemainingCode extends StringPrimitiveValue<PrimitiveValue<String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public HolidayRemainingCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
