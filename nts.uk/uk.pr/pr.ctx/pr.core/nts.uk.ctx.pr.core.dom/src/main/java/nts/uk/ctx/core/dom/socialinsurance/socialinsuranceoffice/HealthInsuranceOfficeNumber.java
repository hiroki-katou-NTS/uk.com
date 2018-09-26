package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 健康保険事業所番号
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(5)
public class HealthInsuranceOfficeNumber extends StringPrimitiveValue<HealthInsuranceOfficeNumber> {

    private static final long serialVersionUID = 1L;

    public HealthInsuranceOfficeNumber(String rawValue) {
        super(rawValue);
    }
}
