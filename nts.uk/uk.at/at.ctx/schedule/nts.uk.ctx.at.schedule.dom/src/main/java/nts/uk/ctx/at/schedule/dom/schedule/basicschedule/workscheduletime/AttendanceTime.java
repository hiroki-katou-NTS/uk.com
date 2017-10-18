package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
//勤怠時間
@TimeRange(min = "0:00", max = "48:00")
public class AttendanceTime extends TimeClockPrimitiveValue<AttendanceTime>  {

	private static final long serialVersionUID = 1L;

	public AttendanceTime(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

}
