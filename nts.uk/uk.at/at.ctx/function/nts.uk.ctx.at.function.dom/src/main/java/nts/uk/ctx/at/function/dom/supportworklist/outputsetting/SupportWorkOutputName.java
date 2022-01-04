package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 応援勤務表名称
 */
@StringMaxLength(40)
public class SupportWorkOutputName extends StringPrimitiveValue<SupportWorkOutputName> {
    public SupportWorkOutputName(String rawValue) {
        super(rawValue);
    }
}
