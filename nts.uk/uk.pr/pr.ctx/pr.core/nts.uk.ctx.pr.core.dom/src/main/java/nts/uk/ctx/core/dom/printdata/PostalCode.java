package nts.uk.ctx.core.dom.printdata;


import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 郵便番号
 */
@StringMaxLength(8)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class PostalCode extends StringPrimitiveValue<nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.PostalCode> {
    private static final long serialVersionUID = 1L;

    public PostalCode(String rawValue) {
        super(rawValue);
    }
}
