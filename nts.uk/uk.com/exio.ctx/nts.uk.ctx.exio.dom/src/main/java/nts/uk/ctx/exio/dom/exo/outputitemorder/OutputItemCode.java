package nts.uk.ctx.exio.dom.exo.outputitemorder;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(3)
public class OutputItemCode extends StringPrimitiveValue<OutputItemCode>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutputItemCode(String rawValue) {
		super(rawValue);
	}
}
