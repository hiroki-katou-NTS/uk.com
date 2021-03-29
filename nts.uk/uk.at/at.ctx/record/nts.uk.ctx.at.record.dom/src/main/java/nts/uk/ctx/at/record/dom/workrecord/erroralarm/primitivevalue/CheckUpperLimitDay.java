package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerMinValue;

/**
 * チェック上限日数
 *
 */
@HalfIntegerMinValue(0.0)
@HalfIntegerMaxValue(99.9)
public class CheckUpperLimitDay  extends HalfIntegerPrimitiveValue<CheckUpperLimitDay> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new value.
	 *
	 * @param rawValue the raw value
	 */	
	public CheckUpperLimitDay(Double rawValue) {
		super(rawValue);
	}
	
}