package nts.uk.ctx.bs.person.dom.person.info.timepointitem;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(max = "71：59", min = "－12：00")
public class TimeWithDayAttr extends TimeDurationPrimitiveValue<TimeWithDayAttr> {

	private static final long serialVersionUID = 1L;

	public TimeWithDayAttr(Long rawValue) {
		super(rawValue);
	}
}
