package nts.uk.ctx.at.record.dom.daily;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 勤務回数
 * @author keisuke_hoshina
 *
 */
@IntegerRange(max = 5, min = 0)
public class WorkTimes extends IntegerPrimitiveValue<WorkTimes>{
	
	
	private static final long serialVersionUID = 1L;
	
	public WorkTimes(int rawValue) {
		super(rawValue);
	}
}
