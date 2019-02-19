package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;

/**
 * 郵便番号
 */
@StringMaxLength(8)
@StringRegEx("^\\d{7}$|(^\\d{3}[-]\\d{4}?$)")
@StringCharType(CharType.ANY_HALF_WIDTH)
public class PostalCode extends StringPrimitiveValue<PostalCode> {
    private static final long serialVersionUID = 1L;

    public PostalCode(String rawValue) {
        super(rawValue);
    }
}
