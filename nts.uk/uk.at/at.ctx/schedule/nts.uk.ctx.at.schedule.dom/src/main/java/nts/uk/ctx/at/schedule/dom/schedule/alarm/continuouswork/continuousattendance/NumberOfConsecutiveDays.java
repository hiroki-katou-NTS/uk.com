package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 連続できる日数
 * @author lan_lt
 *
 */
@IntegerRange(min = 1, max = 31)
public class NumberOfConsecutiveDays extends IntegerPrimitiveValue<NumberOfConsecutiveDays>{

	public NumberOfConsecutiveDays(Integer rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
