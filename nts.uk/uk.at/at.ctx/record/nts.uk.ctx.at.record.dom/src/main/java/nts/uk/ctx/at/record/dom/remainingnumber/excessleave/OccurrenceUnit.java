package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min ="0:00", max="999:59")
public class OccurrenceUnit extends TimeDurationPrimitiveValue<OccurrenceUnit>{

	private static final long serialVersionUID = 1L;

	public OccurrenceUnit(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

}
