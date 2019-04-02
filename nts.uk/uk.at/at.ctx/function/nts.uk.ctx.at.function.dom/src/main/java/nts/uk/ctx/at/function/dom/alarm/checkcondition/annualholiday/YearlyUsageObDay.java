package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author thanhnx
 * 年休使用義務日数
 */
@IntegerRange(min = 1, max = 99)
public class YearlyUsageObDay extends IntegerPrimitiveValue<YearlyUsageObDay>{

	private static final long serialVersionUID = 1L;
	
	public YearlyUsageObDay(Integer rawValue) {
		super(rawValue);
	}

}
