package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeMinValue;
/**
 * 
 * @author hieult
 *
 */
@TimeMaxValue("23:59")
@TimeMinValue("00:00")
public class TimeDay extends TimeClockPrimitiveValue<TimeDay> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public TimeDay(int minutes) {
		super(minutes);
	}	
}

