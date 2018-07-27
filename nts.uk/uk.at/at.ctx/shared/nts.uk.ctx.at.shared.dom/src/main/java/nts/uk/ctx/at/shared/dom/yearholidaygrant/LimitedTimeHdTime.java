package nts.uk.ctx.at.shared.dom.yearholidaygrant;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 時間年休上限時間
 * @author shuichu_ishida
 */
@TimeRange(min ="0:00", max="999:59")
public class LimitedTimeHdTime extends TimeDurationPrimitiveValue<LimitedTimeHdTime> {

	private static final long serialVersionUID = 1L;

	public LimitedTimeHdTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}
}
