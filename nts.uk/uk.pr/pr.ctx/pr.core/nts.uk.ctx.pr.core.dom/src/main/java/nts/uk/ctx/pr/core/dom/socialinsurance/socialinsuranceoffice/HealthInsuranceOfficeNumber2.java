package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 健康保険事業所整理番号2
 */
@StringMaxLength(8)
public class HealthInsuranceOfficeNumber2 extends StringPrimitiveValue<HealthInsuranceOfficeNumber2> {

    private static final long serialVersionUID = 1L;

    public HealthInsuranceOfficeNumber2(String rawValue) {
        super(rawValue);
    }
}