package nts.uk.ctx.pereg.dom.person.info.timeitem;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(max = "9999:59", min = "0:00")
public class TimeItemMax extends TimeDurationPrimitiveValue<TimeItemMax> {

	private static final long serialVersionUID = 1L;

	public TimeItemMax(int timeAsMinutes) {
		super(timeAsMinutes);
	}
}
