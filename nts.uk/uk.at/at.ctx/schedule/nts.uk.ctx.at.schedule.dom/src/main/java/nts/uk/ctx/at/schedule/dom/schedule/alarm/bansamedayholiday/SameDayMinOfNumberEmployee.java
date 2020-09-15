package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 同日出勤下限人数
 * @author lan_lt
 *
 */
@IntegerRange(min = 0, max = 10000)
public class SameDayMinOfNumberEmployee extends IntegerPrimitiveValue<SameDayMinOfNumberEmployee>{

	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	
	public SameDayMinOfNumberEmployee(Integer rawValue) {
		super(rawValue);
	}

}
