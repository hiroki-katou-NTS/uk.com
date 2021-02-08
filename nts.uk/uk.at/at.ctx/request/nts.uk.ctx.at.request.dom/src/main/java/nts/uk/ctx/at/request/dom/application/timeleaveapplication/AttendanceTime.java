package nts.uk.ctx.at.request.dom.application.timeleaveapplication;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 勤怠時間
 */
@TimeRange(min = "00:00", max = "48:00")
public class AttendanceTime extends TimeDurationPrimitiveValue<AttendanceTime> {

	private static final long serialVersionUID = 1L;

	public AttendanceTime(Integer rawValue) {
		super(rawValue);
	}

}
