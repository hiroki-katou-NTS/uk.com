package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * PrimitiveValue: 振休時間
 *
 */
@TimeRange(min = "00:00", max = "999:59")
public class HolidayTime extends TimeDurationPrimitiveValue<HolidayTime> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new time duration.
	 *
	 * @param timeAsMinutes the time as minutes
	 */
	public HolidayTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}
	
}

