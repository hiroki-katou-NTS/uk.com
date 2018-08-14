package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author MinhVV
 *
 */
//月数
@IntegerRange(min = 0, max = 11)
public class CheckedNumberMonthsValue  extends IntegerPrimitiveValue<CheckedNumberMonthsValue> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new times value.
	 *
	 * @param rawValue the raw value
	 */	
	public CheckedNumberMonthsValue(Integer rawValue) {
		super(rawValue);
	}
	
}
