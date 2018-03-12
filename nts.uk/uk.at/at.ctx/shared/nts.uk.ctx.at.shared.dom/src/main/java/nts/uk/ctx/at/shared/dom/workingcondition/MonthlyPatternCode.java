package nts.uk.ctx.at.shared.dom.workingcondition;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class MonthlyPatternCode.
 */
// 月間パターンコード
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
@ZeroPaddedCode
public class MonthlyPatternCode extends StringPrimitiveValue<MonthlyPatternCode>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7718374493366792675L;
	
	/**
	 * Instantiates a new monthly pattern code.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyPatternCode(String rawValue) {
		super(rawValue);
	}
}
