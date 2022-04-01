package nts.uk.ctx.alarm.dom;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * チェック条件コード
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
public class AlarmListCheckerCode extends CodePrimitiveValue<AlarmListCheckerCode> {

    /**
     * Constructs.
     *
     * @param rawValue raw value
     */
    public AlarmListCheckerCode(String rawValue) {
        super(rawValue);
    }
}
