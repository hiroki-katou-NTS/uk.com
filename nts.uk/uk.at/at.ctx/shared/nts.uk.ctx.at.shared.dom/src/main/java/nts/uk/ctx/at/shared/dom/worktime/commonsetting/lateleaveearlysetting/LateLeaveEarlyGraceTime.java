package nts.uk.ctx.at.shared.dom.worktime.commonsetting.lateleaveearlysetting;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * 遅刻早退猶予時間
 * @author ken_takasu
 *
 */
@TimeRange(min = "0:00" , max = "72:00")
public class LateLeaveEarlyGraceTime extends TimeDurationPrimitiveValue<LateLeaveEarlyGraceTime>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public LateLeaveEarlyGraceTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}
}
