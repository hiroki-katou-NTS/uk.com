package nts.uk.ctx.basic.dom.system.bank.linebank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class RequesterName extends StringPrimitiveValue<RequesterName> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public RequesterName(String arg0) {
		// TODO Auto-generated constructor stub
		super(arg0);
	}
}
