/**
 * 5:43:29 PM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author hungnm
 *
 */
//チェック条件値金額
@IntegerRange(min = -99999999, max = 999999999)
public class CheckedAmountValue extends IntegerPrimitiveValue<CheckedAmountValue> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new amount value.
	 *
	 * @param rawValue the raw value
	 */
	public CheckedAmountValue(Integer rawValue) {
		super(rawValue);
	}
	
}
