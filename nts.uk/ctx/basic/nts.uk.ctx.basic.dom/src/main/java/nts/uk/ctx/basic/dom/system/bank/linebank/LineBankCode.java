package nts.uk.ctx.basic.dom.system.bank.linebank;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(2)
public class LineBankCode extends CodePrimitiveValue<LineBankCode> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public LineBankCode(String rawValue) {
		// TODO Auto-generated constructor stub
		super(rawValue);
	}
}
