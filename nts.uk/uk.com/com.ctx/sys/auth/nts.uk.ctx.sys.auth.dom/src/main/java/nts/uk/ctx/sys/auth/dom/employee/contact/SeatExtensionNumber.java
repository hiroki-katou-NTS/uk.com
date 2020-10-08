package nts.uk.ctx.sys.auth.dom.employee.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 座席内線番号
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(20)
public class SeatExtensionNumber extends StringPrimitiveValue<SeatExtensionNumber> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SeatExtensionNumber(String rawValue) {
        super(rawValue);
    }
}
