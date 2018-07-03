package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "00:00", max = "48:00")
public class AttendanceTime extends TimeDurationPrimitiveValue<AttendanceTime> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttendanceTime(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

}
