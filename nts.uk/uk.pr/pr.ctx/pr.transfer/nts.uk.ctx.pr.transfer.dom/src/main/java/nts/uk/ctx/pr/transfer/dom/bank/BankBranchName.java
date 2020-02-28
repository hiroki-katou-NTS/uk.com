package nts.uk.ctx.pr.transfer.dom.bank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT - 銀行支店名
 *
 */
@StringMaxLength(20)
public class BankBranchName extends StringPrimitiveValue<BankBranchName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankBranchName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
