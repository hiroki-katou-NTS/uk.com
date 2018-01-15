/**
 * 5:44:01 PM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author hungnm
 *
 */
//チェック条件値回数
@IntegerRange(min = -99999999, max = 999999999)
public class CheckedTimesValue  extends IntegerPrimitiveValue<CheckedTimesValue> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new times value.
	 *
	 * @param rawValue the raw value
	 */	
	public CheckedTimesValue(Integer rawValue) {
		super(rawValue);
	}
	
}
