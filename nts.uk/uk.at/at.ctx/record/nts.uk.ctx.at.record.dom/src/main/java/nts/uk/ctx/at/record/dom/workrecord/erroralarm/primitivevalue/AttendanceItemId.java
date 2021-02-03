/**
 * 5:43:29 PM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * @author hungnm
 *
 */
//チェック条件値金額
@IntegerMinValue(0)
public class AttendanceItemId extends IntegerPrimitiveValue<AttendanceItemId> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new amount value.
	 *
	 * @param rawValue the raw value
	 */
	public AttendanceItemId(Integer rawValue) {
		super(rawValue);
	}
	
}
