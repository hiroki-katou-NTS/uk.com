package nts.uk.ctx.at.record.dom.workinformation.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitWeek;

/**
 * 
 * @author nampt
 * 時刻(日区分付き)
 *
 */
@TimeRange(max="71:59", min = "-12:00")
public class TimeWithDayAtr extends TimeDurationPrimitiveValue<LimitWeek> {
	
	public TimeWithDayAtr(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
