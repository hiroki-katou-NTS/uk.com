package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

//  時間 - 特別休暇残時間
@TimeRange(min = "-999:59", max = "999:59")
public class TimeOfRemain extends TimeDurationPrimitiveValue<TimeOfRemain>{

	/**
	 * Instantiates a new time duration.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	private static final long serialVersionUID = 1L;

	public TimeOfRemain(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
