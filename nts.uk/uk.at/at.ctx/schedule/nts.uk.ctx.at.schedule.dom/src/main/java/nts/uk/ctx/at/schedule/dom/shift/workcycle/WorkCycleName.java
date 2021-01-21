package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
    勤務サイクル名称
 */
@StringMaxLength(12)
public class WorkCycleName extends StringPrimitiveValue<WorkCycleName> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2960364556648891076L;

    /**
     * Instantiates a new priority.
     *
     * @param rawValue
     *            the raw value
     */
    public WorkCycleName(String rawValue) {
        super(rawValue);
    }

}

