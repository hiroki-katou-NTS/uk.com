package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * 対象項目コード
 *
 */

@StringMaxLength(4)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class TargetItemCode extends CodePrimitiveValue<TargetItemCode> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public TargetItemCode(String rawValue) {
		super(rawValue);
	}

}
