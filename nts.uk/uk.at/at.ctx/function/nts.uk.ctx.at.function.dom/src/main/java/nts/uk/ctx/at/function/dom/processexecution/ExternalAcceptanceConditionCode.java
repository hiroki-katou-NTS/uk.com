package nts.uk.ctx.at.function.dom.processexecution;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 任意集計枠コード
 * @author ngatt-nws
 *
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class ExternalAcceptanceConditionCode extends CodePrimitiveValue<ExternalAcceptanceConditionCode> {
	private static final long serialVersionUID = 1L;

	public ExternalAcceptanceConditionCode(String rawValue) {
		super(rawValue);
	}
}
