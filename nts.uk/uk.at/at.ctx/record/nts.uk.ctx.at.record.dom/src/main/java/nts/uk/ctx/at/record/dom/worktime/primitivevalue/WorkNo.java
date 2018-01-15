package nts.uk.ctx.at.record.dom.worktime.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author nampt
 * 勤務NO
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(3)
public class WorkNo extends IntegerPrimitiveValue<WorkNo>{

	private static final long serialVersionUID = 1L;
	
	public WorkNo(Integer rawValue) {
		super(rawValue);
	}
}
