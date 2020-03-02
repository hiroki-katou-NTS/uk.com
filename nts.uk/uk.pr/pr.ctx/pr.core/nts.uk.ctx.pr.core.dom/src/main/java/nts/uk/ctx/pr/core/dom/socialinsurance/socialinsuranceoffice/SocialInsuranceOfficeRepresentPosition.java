package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 社会保険事業所代表者職位
 */
@StringMaxLength(10)
public class SocialInsuranceOfficeRepresentPosition extends StringPrimitiveValue<SocialInsuranceOfficeRepresentPosition> {

    private static final long serialVersionUID = 1L;

    public SocialInsuranceOfficeRepresentPosition(String rawValue) {
        super(rawValue);
    }
}
