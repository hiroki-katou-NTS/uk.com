package nts.uk.ctx.at.record.dom.worktime.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 
 * @author nampt
 * 勤務NO
 *
 */
@DecimalMinValue("1")
@DecimalMaxValue("3")
public class WorkNo extends IntegerPrimitiveValue<WorkNo>{

	private static final long serialVersionUID = 1L;
	
	public WorkNo(Integer rawValue) {
		super(rawValue);
	}
}
