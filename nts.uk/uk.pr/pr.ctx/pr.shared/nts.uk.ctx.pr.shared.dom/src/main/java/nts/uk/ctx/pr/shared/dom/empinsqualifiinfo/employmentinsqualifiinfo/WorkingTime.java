package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 一週間の所定労働時間
 */
@IntegerMinValue(0)
@IntegerMaxValue(59999)
public class WorkingTime extends IntegerPrimitiveValue<WorkingTime> {
    public WorkingTime(Integer rawValue) {
        super(rawValue);
    }
}
