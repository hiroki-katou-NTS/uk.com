package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;


import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 *  作業枠名
 */
@StringMaxLength(12)
public class TaskFrameName extends StringPrimitiveValue<TaskFrameName> {

    private static final long serialVersionUID = 1L;
    public TaskFrameName(String rawValue) {
        super(rawValue);
    }
}
