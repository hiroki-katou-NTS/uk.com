package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "0:00", max="999:59")
public class AnnualLeaveUsedTime extends TimeDurationPrimitiveValue<AnnualLeaveUsedTime>{

	private static final long serialVersionUID = -1832569387468737744L;

	public AnnualLeaveUsedTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
