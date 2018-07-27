package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * min max integer
 * @author tutk
 *
 */
@IntegerRange(max = 999999999, min = 0)
public class MinMaxInteger extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {

	public MinMaxInteger(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
