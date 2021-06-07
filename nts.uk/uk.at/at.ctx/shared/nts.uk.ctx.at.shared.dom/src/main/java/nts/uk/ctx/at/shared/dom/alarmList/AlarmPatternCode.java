package nts.uk.ctx.at.shared.dom.alarmList;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class AlarmPatternCode extends CodePrimitiveValue<AlarmPatternCode> {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new role code.
     *
     * @param rawValue the raw value
     */
    public AlarmPatternCode(String rawValue) {
        super(rawValue);
    }
}
