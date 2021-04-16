package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * PrimitiveValue: 年間の月
 *
 */
@IntegerRange(min = 1, max = 12)
public class MonthOfYear extends IntegerPrimitiveValue<MonthOfYear> {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new value.
	 *
	 * @param rawValue the raw value
	 */
	public MonthOfYear(Integer rawValue) {
		super(rawValue);
	}
}
