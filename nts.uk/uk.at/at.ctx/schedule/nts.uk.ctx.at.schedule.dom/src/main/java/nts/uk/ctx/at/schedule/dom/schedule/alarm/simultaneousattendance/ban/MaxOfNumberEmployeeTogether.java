package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 同時出勤上限人数
 * @author lan_lt
 *
 */
@IntegerRange(min = 0, max = 10000)
public class MaxOfNumberEmployeeTogether extends IntegerPrimitiveValue<MaxOfNumberEmployeeTogether>{

	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	
	public MaxOfNumberEmployeeTogether(Integer rawValue) {
		super(rawValue);
	}

}
