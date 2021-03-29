package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerMinValue;

/**
 * 
 * チェック上限
 *
 */
@HalfIntegerMinValue(-999.9)
@HalfIntegerMaxValue(999.9)
public class CheckUpperLimit extends HalfIntegerPrimitiveValue<CheckUpperLimit> {
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new value.
	 *
	 * @param rawValue the raw value
	 */	
	public CheckUpperLimit(Double rawValue) {
		super(rawValue);
	}
}
