package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 応援勤務表コード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class SupportWorkOutputCode extends StringPrimitiveValue<SupportWorkOutputCode> {
    public SupportWorkOutputCode(String rawValue) {
        super(rawValue);
    }
}
