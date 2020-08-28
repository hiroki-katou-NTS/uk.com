package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/*
    勤務の日数
 */
@IntegerRange(max = 31, min = 1)
public class NumOfWorkingDays extends IntegerPrimitiveValue<NumOfWorkingDays> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new number day calendar.
     *
     * @param rawValue the raw value
     */
    public NumOfWorkingDays(Integer rawValue) {
        super(rawValue);
    }

}