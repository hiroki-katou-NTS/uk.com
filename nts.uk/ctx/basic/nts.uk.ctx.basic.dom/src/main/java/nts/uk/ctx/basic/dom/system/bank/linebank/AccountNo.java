package nts.uk.ctx.basic.dom.system.bank.linebank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(7)
public class AccountNo extends StringPrimitiveValue<AccountNo> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public AccountNo(String arg0) {
		// TODO Auto-generated constructor stub
		super(arg0);
	}
}
