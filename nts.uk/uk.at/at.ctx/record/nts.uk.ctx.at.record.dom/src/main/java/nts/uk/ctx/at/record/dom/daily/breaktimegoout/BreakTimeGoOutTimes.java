package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 休憩外出回数
 *
 */
//@IntegerRange(max = 5, min = 0)
@IntegerMinValue(0)
public class BreakTimeGoOutTimes extends IntegerPrimitiveValue<BreakTimeGoOutTimes>{
	
	
	private static final long serialVersionUID = 1L;
	
	public BreakTimeGoOutTimes(int rawValue) {
		super(rawValue);
	}
}
