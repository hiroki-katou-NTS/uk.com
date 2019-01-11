package nts.uk.ctx.pr.transfer.dom.sourcebank;

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

@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class SourceBankCode extends CodePrimitiveValue<SourceBankCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SourceBankCode(String rawValue) {
		super(rawValue);
	}

}
