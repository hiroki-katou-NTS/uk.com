package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 厚生年金基金番号
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(4)
public class WelfarePensionFundNumber extends StringPrimitiveValue<WelfarePensionFundNumber> {

    private static final long serialVersionUID = 1L;

    public WelfarePensionFundNumber(String rawValue) {
        super(rawValue);
    }
}
