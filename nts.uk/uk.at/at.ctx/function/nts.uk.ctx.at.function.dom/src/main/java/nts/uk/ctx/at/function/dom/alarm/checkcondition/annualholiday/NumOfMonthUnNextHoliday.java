package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author thanhnx
 * 次回年休付与日までの月数
 */
@IntegerRange(min = 1, max = 12)
public class NumOfMonthUnNextHoliday extends IntegerPrimitiveValue<NumOfMonthUnNextHoliday>{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	public NumOfMonthUnNextHoliday(Integer rawValue) {
		super(rawValue);
	}
}
