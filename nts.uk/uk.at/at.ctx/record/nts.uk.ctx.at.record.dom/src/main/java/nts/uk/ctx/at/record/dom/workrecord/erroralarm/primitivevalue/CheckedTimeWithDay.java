package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
import nts.uk.shr.com.time.TimeWithDayAttr;

@TimeRange(min = "-12:00", max = "71:59")
public class CheckedTimeWithDay extends TimeWithDayAttr implements CheckConditionValue<PrimitiveValue<Integer>> {

	/**  */
	private static final long serialVersionUID = 1L;

	public CheckedTimeWithDay(int minutesFromZeroOClock) {
		super(minutesFromZeroOClock);
	}

	@Override
	public Integer value() {
		return this.valueAsMinutes();
	}

}
