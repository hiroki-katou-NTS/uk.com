package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 任意集計枠コード
 * @author phongtq
 *
 */

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
@ZeroPaddedCode
public class AggrFrameCode extends StringPrimitiveValue<PrimitiveValue<String>>{
	
	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;

	public AggrFrameCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
