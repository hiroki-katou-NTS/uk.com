package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerMinValue;

/**
 * PrimitiveValue: 平均日数
 *
 */
@HalfIntegerMinValue(0.0)
@HalfIntegerMaxValue(99.9)
public class AverageNumberOfDays extends HalfIntegerPrimitiveValue<AverageNumberOfDays> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new times value.
	 *
	 * @param rawValue the raw value
	 */	
	public AverageNumberOfDays(Double rawValue) {
		super(rawValue);
	}
	
}
