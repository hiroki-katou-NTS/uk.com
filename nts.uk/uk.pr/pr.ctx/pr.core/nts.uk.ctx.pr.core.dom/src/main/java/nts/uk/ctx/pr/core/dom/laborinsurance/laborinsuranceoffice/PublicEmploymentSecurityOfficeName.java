package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 公共職業安定所名
 */
@StringMaxLength(20)
public class PublicEmploymentSecurityOfficeName extends StringPrimitiveValue<PublicEmploymentSecurityOfficeName> {
    private static final long serialVersionUID = 1L;

    public PublicEmploymentSecurityOfficeName(String rawValue) {
        super(rawValue);
    }
}
