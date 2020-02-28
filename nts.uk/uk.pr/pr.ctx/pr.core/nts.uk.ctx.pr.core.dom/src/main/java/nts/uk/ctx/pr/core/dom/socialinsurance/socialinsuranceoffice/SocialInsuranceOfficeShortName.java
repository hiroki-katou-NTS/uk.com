package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 社会保険事業所略名
 */
@StringMaxLength(10)
public class SocialInsuranceOfficeShortName extends StringPrimitiveValue<SocialInsuranceOfficeShortName> {

    private static final long serialVersionUID = 1L;

    public SocialInsuranceOfficeShortName(String rawValue) {
        super(rawValue);
    }
}
