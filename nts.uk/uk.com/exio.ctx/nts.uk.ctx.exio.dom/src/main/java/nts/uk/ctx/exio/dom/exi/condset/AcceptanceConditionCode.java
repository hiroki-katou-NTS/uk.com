package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author HungTT
 * 外部受入条件コード
 *
 */

@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class AcceptanceConditionCode extends CodePrimitiveValue<AcceptanceConditionCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AcceptanceConditionCode(String rawValue) {
		super(rawValue);
	}

}
