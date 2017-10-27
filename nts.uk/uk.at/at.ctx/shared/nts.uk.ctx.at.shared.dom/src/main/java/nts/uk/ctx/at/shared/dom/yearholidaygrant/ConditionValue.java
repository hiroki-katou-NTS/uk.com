package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

@IntegerMaxValue(999)
public class ConditionValue extends IntegerPrimitiveValue<ConditionValue> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConditionValue(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
