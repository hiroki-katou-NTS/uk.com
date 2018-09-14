package nts.uk.ctx.at.record.dom.daily;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 勤務回数
 * @author keisuke_hoshina
 *
 */
@IntegerRange(max = 5, min = 0)
@ZeroPaddedCode
public class WorkTimes extends IntegerPrimitiveValue<WorkTimes>{
	
	
	private static final long serialVersionUID = 1L;
	
	public WorkTimes(int rawValue) {
		super(rawValue);
	}
}
