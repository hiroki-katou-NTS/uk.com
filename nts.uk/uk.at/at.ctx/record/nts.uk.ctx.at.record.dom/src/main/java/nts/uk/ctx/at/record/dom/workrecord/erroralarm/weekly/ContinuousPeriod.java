/**
 * 10:24:32 AM Jan 19, 2018
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

//連続期間
@IntegerRange(min = 0, max = 99)
public class ContinuousPeriod extends IntegerPrimitiveValue<ContinuousPeriod> {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new ContinuousPeriod value.
	 *
	 * @param rawValue the raw value
	 */
	public ContinuousPeriod(Integer rawValue) {
		super(rawValue);
	}
	
}
