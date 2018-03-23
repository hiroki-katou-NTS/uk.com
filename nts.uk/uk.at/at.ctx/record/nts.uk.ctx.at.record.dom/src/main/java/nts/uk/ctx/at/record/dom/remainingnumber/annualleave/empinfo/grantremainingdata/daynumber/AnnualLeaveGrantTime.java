package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "0:00", max="999:59")
public class AnnualLeaveGrantTime extends TimeDurationPrimitiveValue<AnnualLeaveGrantTime>{

	private static final long serialVersionUID = 4781404898136447144L;

	public AnnualLeaveGrantTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
