package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "-12:00", max = "71:00")
public class WorkplaceCounterStartTime extends TimeDurationPrimitiveValue<WorkplaceCounterStartTime> {

	public WorkplaceCounterStartTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	private static final long serialVersionUID = -313910965196797184L;

}
