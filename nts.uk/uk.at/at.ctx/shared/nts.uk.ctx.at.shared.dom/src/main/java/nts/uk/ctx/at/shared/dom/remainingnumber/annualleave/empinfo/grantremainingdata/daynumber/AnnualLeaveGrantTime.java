package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.constraint.TimeRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantTime;

@TimeRange(min = "0:00", max="999:59")
public class AnnualLeaveGrantTime extends LeaveGrantTime{

	private static final long serialVersionUID = 4781404898136447144L;

	public AnnualLeaveGrantTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
