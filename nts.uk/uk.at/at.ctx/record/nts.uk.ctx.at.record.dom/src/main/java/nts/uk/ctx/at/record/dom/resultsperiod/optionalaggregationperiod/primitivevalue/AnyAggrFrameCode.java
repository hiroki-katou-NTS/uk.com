package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 任意集計枠コード
 *
 * @author phongtq
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
@ZeroPaddedCode
public class AnyAggrFrameCode extends CodePrimitiveValue<AnyAggrFrameCode> {

	private static final long serialVersionUID = -9062183713906522324L;

	/**
	 * コンストラクタ
	 *
	 * @param code コード
	 */
	public AnyAggrFrameCode(String code) {
		super(code);
	}

}
