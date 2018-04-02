package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author HungTT
 * 外部受入条件コード
 *
 */

@StringMaxLength(3)
@StringRegEx("^[a-zA-Z0-9_-]{1,3}$")
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
