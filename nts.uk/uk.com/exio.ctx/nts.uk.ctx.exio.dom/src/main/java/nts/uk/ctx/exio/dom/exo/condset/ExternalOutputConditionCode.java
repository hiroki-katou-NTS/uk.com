package nts.uk.ctx.exio.dom.exo.condset;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 外部出力条件コード
 */
@StringMaxLength(3)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class ExternalOutputConditionCode extends CodePrimitiveValue<ExternalOutputConditionCode> {

	private static final long serialVersionUID = 1L;

	public ExternalOutputConditionCode(String rawValue) {
		super(rawValue);
	}

}
