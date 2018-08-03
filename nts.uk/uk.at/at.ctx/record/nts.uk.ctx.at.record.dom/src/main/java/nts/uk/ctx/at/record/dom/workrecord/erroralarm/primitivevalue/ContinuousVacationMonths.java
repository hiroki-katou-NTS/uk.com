package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;


/* 該当月回数 */
@HalfIntegerRange(min = 0, max = 12)
public class ContinuousVacationMonths extends IntegerPrimitiveValue<ContinuousVacationMonths> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContinuousVacationMonths(Integer rawValue) {
		super(rawValue);
	}

}
