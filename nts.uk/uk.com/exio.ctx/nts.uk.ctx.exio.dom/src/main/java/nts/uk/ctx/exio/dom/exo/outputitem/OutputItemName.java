package nts.uk.ctx.exio.dom.exo.outputitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class OutputItemName extends StringPrimitiveValue<OutputItemName> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutputItemName(String rawValue) {
		super(rawValue);
	}
}
