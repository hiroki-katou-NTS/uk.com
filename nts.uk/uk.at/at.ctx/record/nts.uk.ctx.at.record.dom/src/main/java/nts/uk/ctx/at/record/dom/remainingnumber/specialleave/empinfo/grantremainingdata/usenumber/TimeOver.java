package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

// 時間 - 特別休暇付与時間
@TimeRange(min = "00:00", max = "999:59")
public class TimeOver extends TimeDurationPrimitiveValue<TimeOver> {

	/**
	 * Instantiates a new time duration.
	 *
	 * @param timeAsMinutes
	 *            the time as minutes
	 */
	private static final long serialVersionUID = 1L;

	public TimeOver(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
