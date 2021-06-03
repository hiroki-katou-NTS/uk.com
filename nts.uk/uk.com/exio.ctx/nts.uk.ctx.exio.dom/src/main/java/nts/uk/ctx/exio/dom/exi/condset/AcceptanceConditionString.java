package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class AcceptanceConditionString extends StringPrimitiveValue<AcceptanceConditionString>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AcceptanceConditionString(String rawValue) {
		super(rawValue);
	}

}
