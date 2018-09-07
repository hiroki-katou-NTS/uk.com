package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 健保組合事業所番号
 */
@StringMaxLength(4)
@StringCharType(CharType.ALPHA_NUMERIC)
public class HealthInsuranceUnionOfficeNumber extends StringPrimitiveValue<HealthInsuranceUnionOfficeNumber> {

    private static final long serialVersionUID = 1L;

    public HealthInsuranceUnionOfficeNumber(String rawValue) {
        super(rawValue);
    }

}
