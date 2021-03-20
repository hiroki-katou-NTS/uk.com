package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * チェック上限対比
 * @author VietTx
 *
 */
@IntegerRange(min = -999, max = 999)
public class CheckUpperLimitComparison extends IntegerPrimitiveValue<CheckUpperLimitComparison> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates value
	 *
	 * @param value the number of times
	 */
	public CheckUpperLimitComparison(int value) {
		super(value);
	}
}
