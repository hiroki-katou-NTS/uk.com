package nts.uk.ctx.pr.transfer.dom.desbank;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author HungTT - 振込元銀行コード
 *
 */

@StringMaxLength(4)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class DesBankCode extends CodePrimitiveValue<DesBankCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DesBankCode(String rawValue) {
		super(rawValue);
	}

}
