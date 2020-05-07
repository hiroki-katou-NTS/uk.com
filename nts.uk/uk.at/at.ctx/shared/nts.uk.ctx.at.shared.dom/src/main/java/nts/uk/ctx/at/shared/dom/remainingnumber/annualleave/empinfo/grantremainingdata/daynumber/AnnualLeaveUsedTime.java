package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.constraint.TimeRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedTime;

@TimeRange(min = "0:00", max="999:59")
public class AnnualLeaveUsedTime extends LeaveUsedTime{

	private static final long serialVersionUID = -1832569387468737744L;

	public AnnualLeaveUsedTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
