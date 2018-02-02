package nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeMinValue;

/**
 * 終了時刻
 */
@TimeMaxValue("23:59")
@TimeMinValue("00:00")
public class EndTime extends TimeClockPrimitiveValue<EndTime>{
	private static final long serialVersionUID = 1L;

	public EndTime(int rawValue) {
		super(rawValue);
	}
}
