package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * @author nampt
 * 勤務回数
 *
 */
@IntegerMaxValue(5)
@IntegerMinValue(0)
public class WorkTimes extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public WorkTimes(Integer rawValue) {
		super(rawValue);
	}
}
