package nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class OutItemsWoScName extends StringPrimitiveValue<OutItemsWoScName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutItemsWoScName(String rawValue) {
		super(rawValue);
	}
}
