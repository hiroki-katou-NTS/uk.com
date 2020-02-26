package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

import nts.arc.primitive.TimeClockPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 時間値
 */
@TimeRange(min = "-999:59", max = "999:59")
public class TimeValue extends TimeClockPrimitiveValue<TimeValue> {
    private static final long serialVersionUID = 1L;

    public TimeValue(int minutesFromZeroOClock) {
        super(minutesFromZeroOClock);
    }
}
