package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/*
    勤務サイクルコード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class WorkCycleCode extends StringPrimitiveValue<WorkCycleCode> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2960364556648891076L;

    /**
     * Instantiates a new priority.
     *
     * @param rawValue
     *            the raw value
     */
    public WorkCycleCode(String rawValue) {
        super(rawValue);
    }

}