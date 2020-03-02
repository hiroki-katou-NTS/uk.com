package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 一週間の所定労働時間
 */
@TimeRange(min = "0:00", max = "999:59")
public class WorkingTime extends TimeDurationPrimitiveValue<WorkingTime> {
    public WorkingTime(int rawValue) {
        super(rawValue);
    }
}
