package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 社会保険事業所名
 */
@StringMaxLength(30)
public class SocialInsuranceOfficeName extends StringPrimitiveValue<SocialInsuranceOfficeName> {

    private static final long serialVersionUID = 1L;

    public SocialInsuranceOfficeName(String rawValue) {
        super(rawValue);
    }
}
