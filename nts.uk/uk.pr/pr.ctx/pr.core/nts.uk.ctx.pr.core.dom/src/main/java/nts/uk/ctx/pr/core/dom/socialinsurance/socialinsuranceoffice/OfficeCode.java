package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 事業所記号
 */
@StringMaxLength(4)
@StringCharType(CharType.ALPHA_NUMERIC)
public class OfficeCode extends StringPrimitiveValue<OfficeCode> {

    private static final long serialVersionUID = 1L;

    public OfficeCode(String rawValue) {
        super(rawValue);
    }
}
