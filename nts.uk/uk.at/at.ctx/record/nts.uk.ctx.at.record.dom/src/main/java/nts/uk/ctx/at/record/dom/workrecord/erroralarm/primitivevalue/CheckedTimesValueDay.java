/**
 * 5:44:01 PM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerMaxValue;
import nts.arc.primitive.constraint.HalfIntegerMinValue;

/**
 * @author hungnm
 *
 */
//チェック条件値日数
@HalfIntegerMinValue(-99999999.5)
@HalfIntegerMaxValue(999999999.5)
public class CheckedTimesValueDay  extends HalfIntegerPrimitiveValue<CheckedTimesValueDay> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new times value.
	 *
	 * @param rawValue the raw value
	 */	
	public CheckedTimesValueDay(Double rawValue) {
		super(rawValue);
	}
	
}
