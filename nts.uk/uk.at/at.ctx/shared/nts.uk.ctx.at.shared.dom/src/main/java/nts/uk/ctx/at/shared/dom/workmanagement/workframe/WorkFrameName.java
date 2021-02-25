package nts.uk.ctx.at.shared.dom.workmanagement.workframe;


import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *  作業枠名
 */
@StringMaxLength(12)
public class WorkFrameName extends StringPrimitiveValue<WorkFrameName> {

    private static final long serialVersionUID = 1L;
    public WorkFrameName(String rawValue) {
        super(rawValue);
    }
}
