package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author thanhnx
 * 前回年休付与日数
 */
@IntegerRange(min = 0, max = 99)
public class NumberDayAward extends IntegerPrimitiveValue<NumberDayAward>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NumberDayAward(Integer rawValue) {
		super(rawValue);
	}
}
