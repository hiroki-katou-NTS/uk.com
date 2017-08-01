package nts.uk.ctx.bs.person.dom.person.info.timepointitem;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(max = "72：00", min = "－24：00")
public class TimePoint extends TimeDurationPrimitiveValue<TimePoint> {

	private static final long serialVersionUID = 1L;

	public TimePoint(Long rawValue) {
		super(rawValue);
	}
}
