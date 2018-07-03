package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;


@TimeRange(min="00:00",max="23:59")
public class AttendanceClock extends TimeDurationPrimitiveValue<AttendanceClock> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttendanceClock(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

}
