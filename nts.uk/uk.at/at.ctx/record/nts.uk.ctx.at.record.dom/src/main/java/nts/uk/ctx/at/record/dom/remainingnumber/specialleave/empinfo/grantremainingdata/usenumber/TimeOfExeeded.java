package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

// 時間
@TimeRange(min = "-999:59", max = "999:59")
public class TimeOfExeeded extends TimeDurationPrimitiveValue<TimeOfExeeded> {

	/**
	 * Instantiates a new time duration.
	 *
	 * @param timeAsMinutes
	 *            the time as minutes
	 */
	private static final long serialVersionUID = 1L;

	public TimeOfExeeded(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
