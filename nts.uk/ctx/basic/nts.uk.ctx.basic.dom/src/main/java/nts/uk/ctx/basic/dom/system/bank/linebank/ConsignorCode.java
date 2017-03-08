package nts.uk.ctx.basic.dom.system.bank.linebank;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(10)
public class ConsignorCode extends CodePrimitiveValue<ConsignorCode> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ConsignorCode(String rawValue) {
		// TODO Auto-generated constructor stub
		super(rawValue);
	}
}
