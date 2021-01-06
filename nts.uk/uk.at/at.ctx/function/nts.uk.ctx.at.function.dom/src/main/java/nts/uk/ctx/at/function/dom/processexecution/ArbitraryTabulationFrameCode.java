package nts.uk.ctx.at.function.dom.processexecution;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 外部受入条件コード
 * @author ngatt-nws
 *
 */
@StringMaxLength(5)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class ArbitraryTabulationFrameCode extends CodePrimitiveValue<ArbitraryTabulationFrameCode> {
	private static final long serialVersionUID = 1L;

	public ArbitraryTabulationFrameCode(String rawValue) {
		super(rawValue);
	}
}
