package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "0:00", max="999:59")
public abstract class LeaveUsedTime extends TimeDurationPrimitiveValue<LeaveUsedTime>{

	private static final long serialVersionUID = -1832569387468737744L;

	public LeaveUsedTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
