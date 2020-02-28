package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author HungTT
 *
 */

@StringMaxLength(20)
public class AccountName extends StringPrimitiveValue<AccountName> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public AccountName(String arg0) {
		super(arg0);
	}

}
