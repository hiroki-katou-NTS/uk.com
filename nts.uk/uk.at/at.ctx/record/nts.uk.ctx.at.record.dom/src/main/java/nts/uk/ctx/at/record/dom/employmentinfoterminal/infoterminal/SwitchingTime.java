package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * 切替時刻
 * @author dungbn
 *
 */
@TimeRange(min = "0:00", max = "23:59")
public class SwitchingTime extends TimeClockPrimitiveValue<SwitchingTime> {

	public SwitchingTime(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
