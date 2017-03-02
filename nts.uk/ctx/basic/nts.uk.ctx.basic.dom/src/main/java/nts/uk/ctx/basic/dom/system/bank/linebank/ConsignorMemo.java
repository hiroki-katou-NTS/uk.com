package nts.uk.ctx.basic.dom.system.bank.linebank;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class ConsignorMemo extends StringPrimitiveValue<ConsignorMemo>{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ConsignorMemo(String arg0) {
		// TODO Auto-generated constructor stub
		super(arg0);
	}
}
