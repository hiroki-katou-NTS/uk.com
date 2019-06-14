package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 厚生年金事業所番号
 */
@StringMaxLength(7)
@StringCharType(CharType.ALPHA_NUMERIC)
public class WelfarePensionOfficeNumber extends StringPrimitiveValue<WelfarePensionOfficeNumber> {

    private static final long serialVersionUID = 1L;

    public WelfarePensionOfficeNumber(String rawValue) {
        super(rawValue);
    }
}
