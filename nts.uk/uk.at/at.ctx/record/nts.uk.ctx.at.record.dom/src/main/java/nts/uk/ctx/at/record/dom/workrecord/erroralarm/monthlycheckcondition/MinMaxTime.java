package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.arc.primitive.constraint.TimeRange;
import nts.uk.shr.com.primitive.sample.SampleTimeDuration;

@TimeRange(max="999:59", min = "0:00")
public class MinMaxTime extends TimeDurationPrimitiveValue<SampleTimeDuration> {


	public MinMaxTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
