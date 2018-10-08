package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 住所1
 */
@StringMaxLength(20)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class PhoneNumber extends StringPrimitiveValue<PhoneNumber> {
    private static final long serialVersionUID = 1L;

    public PhoneNumber(String rawValue) {
        super(rawValue);
    }
}
