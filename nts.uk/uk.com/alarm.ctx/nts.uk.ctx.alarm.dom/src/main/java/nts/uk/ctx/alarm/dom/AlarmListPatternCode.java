package nts.uk.ctx.alarm.dom;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * アラームリストパターンコード
 */
@StringMaxLength(2)
@StringCharType(CharType.ALPHA_NUMERIC)
public class AlarmListPatternCode extends CodePrimitiveValue<AlarmListPatternCode> {

    /**
     * Constructs.
     *
     * @param rawValue raw value
     */
    public AlarmListPatternCode(String rawValue) {
        super(rawValue);
    }
}
