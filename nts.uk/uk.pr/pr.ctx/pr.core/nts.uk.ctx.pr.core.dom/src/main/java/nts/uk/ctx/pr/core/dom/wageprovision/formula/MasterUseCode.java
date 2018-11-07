package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * 使用マスタコード
 *
 */

@StringMaxLength(10)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class MasterUseCode extends CodePrimitiveValue<MasterUseCode> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MasterUseCode(String rawValue) {
		super(rawValue);
	}

}
