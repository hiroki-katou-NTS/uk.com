package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

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
