package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

// 時間: 特別休暇使用時間
@TimeRange(min = "00:00", max = "999:59")
public class TimeOfUse extends TimeDurationPrimitiveValue<TimeOfUse> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new time duration.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public TimeOfUse(int timeAsMinutes) {
		super(timeAsMinutes);
	}
	
}
