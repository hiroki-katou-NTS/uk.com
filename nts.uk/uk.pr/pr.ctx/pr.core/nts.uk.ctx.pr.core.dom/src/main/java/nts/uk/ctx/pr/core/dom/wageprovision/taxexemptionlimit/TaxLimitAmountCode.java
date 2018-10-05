package nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author thanh.tq 非課税限度額コード
 *
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class TaxLimitAmountCode extends StringPrimitiveValue<PrimitiveValue<String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public TaxLimitAmountCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
