package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 労働保険事業所名
 */
@StringMaxLength(30)
public class LaborInsuranceOfficeName extends StringPrimitiveValue<LaborInsuranceOfficeName> {

    private static final long serialVersionUID = 1L;

    public LaborInsuranceOfficeName(String rawValue) {
        super(rawValue);
    }
}
