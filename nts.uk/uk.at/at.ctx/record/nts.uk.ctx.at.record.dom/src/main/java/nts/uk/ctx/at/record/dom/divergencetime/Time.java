package nts.uk.ctx.at.record.dom.divergencetime;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;


@TimeRange(max = "99:59", min = "00:00")
public class Time extends TimeDurationPrimitiveValue<Time> {
	private static final long serialVersionUID = 1L;

	public Time(int minute) {
		super(minute);
	}
}
