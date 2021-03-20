package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerMinValue;

/**
 * PrimitiveValue: 平均回数
 * @author VietTx
 *
 */
@HalfIntegerMinValue(-99.5)
@HalfIntegerMaxValue(99.9)
public class AverageNumberOfTimes extends HalfIntegerPrimitiveValue<AverageNumberOfTimes> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new time duration.
	 *
	 * @param numberOfTimes the number of times
	 */
	public AverageNumberOfTimes(Double numberOfTimes) {
		super(numberOfTimes);
	}
}
