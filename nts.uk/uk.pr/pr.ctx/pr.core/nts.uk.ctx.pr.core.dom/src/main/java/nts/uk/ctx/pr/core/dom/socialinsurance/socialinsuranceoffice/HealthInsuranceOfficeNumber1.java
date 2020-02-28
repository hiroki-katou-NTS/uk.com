package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 健康保険事業所整理番号1
 */
@StringMaxLength(6)
public class HealthInsuranceOfficeNumber1 extends StringPrimitiveValue<HealthInsuranceOfficeNumber1> {

    private static final long serialVersionUID = 1L;

    public HealthInsuranceOfficeNumber1(String rawValue) {
        super(rawValue);
    }
}
