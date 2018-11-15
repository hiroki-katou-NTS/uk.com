package nts.uk.ctx.pr.core.dom.wageprovision.speclayout.itemrangeset;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 時間値
 */
@TimeRange(min = "-999:59", max = "999:59")
public class TimeValue extends TimeDurationPrimitiveValue<TimeValue>{


	public TimeValue(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	private static final long serialVersionUID = 1L;

}