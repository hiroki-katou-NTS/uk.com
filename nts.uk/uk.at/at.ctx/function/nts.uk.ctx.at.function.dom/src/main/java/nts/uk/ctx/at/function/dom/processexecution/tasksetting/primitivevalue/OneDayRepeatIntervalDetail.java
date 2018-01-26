package nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeMaxValue;
import nts.arc.primitive.constraint.TimeMinValue;

/**
 * 繰り返し間隔時間
 */
@TimeMaxValue("23:59")
@TimeMinValue("00:01")
public class OneDayRepeatIntervalDetail extends TimeClockPrimitiveValue<OneDayRepeatIntervalDetail> {
	private static final long serialVersionUID = 1L;

	public OneDayRepeatIntervalDetail(int rawValue) {
		super(rawValue);
	}
}
