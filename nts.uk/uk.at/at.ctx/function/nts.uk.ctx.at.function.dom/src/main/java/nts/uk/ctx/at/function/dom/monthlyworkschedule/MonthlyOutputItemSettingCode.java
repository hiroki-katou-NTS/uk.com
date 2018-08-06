package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * The Class MonthlyOutputItemSettingCode.
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class MonthlyOutputItemSettingCode extends StringPrimitiveValue<PrimitiveValue<String>> {

	/**
	 * Instantiates a new monthly output item setting code.
	 *
	 * @param rawValue
	 *            the raw value
	 */
	public MonthlyOutputItemSettingCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/** The Constant serialVersionUID. */
	/**
	 * 
	 */
	private static final long serialVersionUID = -6309114792957866322L;

}
