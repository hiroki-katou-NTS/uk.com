package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 健康保険事業所番号
 */
@IntegerRange(min = 0, max = 5)
public class HealthInsuranceOfficeNumber extends IntegerPrimitiveValue<HealthInsuranceOfficeNumber> {

    private static final long serialVersionUID = 1L;

    public HealthInsuranceOfficeNumber(Integer rawValue) {
        super(rawValue);
    }
}
