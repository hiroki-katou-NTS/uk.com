package nts.uk.ctx.pr.transfer.dom.bank;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author HungTT - 銀行支店コード
 *
 */
@StringMaxLength(3)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class BankBranchCode extends CodePrimitiveValue<BankBranchCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankBranchCode(String rawValue) {
		super(rawValue);
	}

}
