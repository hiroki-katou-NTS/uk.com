package nts.uk.ctx.pereg.dom.person.info.timeitem;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(max = "9999:59", min = "0:00")
public class TimeItemMin extends TimeDurationPrimitiveValue<TimeItemMin> {
	private static final long serialVersionUID = 1L;

	public TimeItemMin(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}