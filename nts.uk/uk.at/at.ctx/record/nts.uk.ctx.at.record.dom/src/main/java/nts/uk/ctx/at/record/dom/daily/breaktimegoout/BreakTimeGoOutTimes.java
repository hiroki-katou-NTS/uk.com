package nts.uk.ctx.at.record.dom.daily.breaktimegoout;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 休憩外出回数
 *
 */
@IntegerRange(max = 10, min = 0)
public class BreakTimeGoOutTimes extends IntegerPrimitiveValue<BreakTimeGoOutTimes>{
	
	
	private static final long serialVersionUID = 1L;
	
	public BreakTimeGoOutTimes(int rawValue) {
		super(rawValue);
	}
}
