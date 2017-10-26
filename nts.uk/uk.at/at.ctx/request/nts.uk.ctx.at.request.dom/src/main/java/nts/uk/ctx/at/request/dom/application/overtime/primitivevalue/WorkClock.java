package nts.uk.ctx.at.request.dom.application.overtime.primitivevalue;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * @author loivt
 * 勤務時刻
 */
@IntegerRange(min = 1, max = 9999)
public class WorkClock extends IntegerPrimitiveValue<WorkClock>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkClock(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
