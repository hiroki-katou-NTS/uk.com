package nts.uk.ctx.exio.dom.exi.condset;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMaxValue(9999)
@IntegerMinValue(1)
public class AcceptanceConditionTime extends IntegerPrimitiveValue<AcceptanceConditionTime>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AcceptanceConditionTime(Integer rawValue) {
		super(rawValue);
	}

}
