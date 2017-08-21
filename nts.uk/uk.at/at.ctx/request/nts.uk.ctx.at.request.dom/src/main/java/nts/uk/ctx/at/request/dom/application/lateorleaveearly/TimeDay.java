package nts.uk.ctx.at.request.dom.application.lateorleaveearly;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.gul.util.Time;
/**
 * 
 * @author hieult
 *
 */
public class TimeDay extends TimeClockPrimitiveValue<TimeDay> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public TimeDay(int hour, int minute, int second) {
		super(hour, minute, second);
	}
	
	public TimeDay(long second) {
		super(second);
	}
	
	/*public TimeDay(Time.Value timeValue) {
		super(timeValue);
	}*/
	
}

