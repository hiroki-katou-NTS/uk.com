package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
 * 社会保険事業所代表者名
 */
@StringMaxLength(10)
public class SocialInsuranceOfficeRepresentName extends StringPrimitiveValue<SocialInsuranceOfficeRepresentName> {

    private static final long serialVersionUID = 1L;

    public SocialInsuranceOfficeRepresentName(String rawValue) {
        super(rawValue);
    }
}
