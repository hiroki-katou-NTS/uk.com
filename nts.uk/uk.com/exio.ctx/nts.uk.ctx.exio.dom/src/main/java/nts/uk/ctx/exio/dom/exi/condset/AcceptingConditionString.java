package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class AcceptingConditionString extends StringPrimitiveValue<AcceptingConditionString>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AcceptingConditionString(String rawValue) {
		super(rawValue);
	}

}
