package nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue;

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
@StringMaxLength(3)
@ZeroPaddedCode
public class DailyPerformanceFormatCode extends StringPrimitiveValue<PrimitiveValue<String>>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public DailyPerformanceFormatCode(String rawValue) {
		super(rawValue);
	}
}
