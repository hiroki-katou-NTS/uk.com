package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 休暇付与時間
 * @author masaaki_jinno
 *
 */
@TimeRange(min = "0:00", max="999:59")
public abstract class LeaveGrantTime extends TimeDurationPrimitiveValue<LeaveGrantTime>{

	private static final long serialVersionUID = 4781404898136447144L;

	public LeaveGrantTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

}
