package nts.uk.ctx.at.request.dom.application.stamp;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeMinValue;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@TimeMaxValue("23:59")
@TimeMinValue("00:00")
public class TimeDayClock extends TimeClockPrimitiveValue<TimeDayClock>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7910641588694547958L;

	public TimeDayClock(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
		// TODO Auto-generated constructor stub
	}

}
