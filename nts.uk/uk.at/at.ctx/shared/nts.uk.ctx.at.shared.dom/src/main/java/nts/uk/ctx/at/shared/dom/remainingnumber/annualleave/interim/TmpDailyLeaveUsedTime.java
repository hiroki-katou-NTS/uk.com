package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedTime;

/**
 * 日別休暇使用時間
 * @author masaaki_jinno
 *
 */
@TimeRange(min = "0:00", max="999:59")
public class TmpDailyLeaveUsedTime extends TimeDurationPrimitiveValue<LeaveUsedTime>{

	private static final long serialVersionUID = -1832569387468737744L;

	public TmpDailyLeaveUsedTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	public TmpDailyLeaveUsedTime clone() {
		return new TmpDailyLeaveUsedTime(this.v());
	}
}
