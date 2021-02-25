package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 申請表示名称
 */
@StringMaxLength(8)
public class ApplicationDisplayName extends StringPrimitiveValue<ApplicationDisplayName> {
    public ApplicationDisplayName(String rawValue) {
        super(rawValue);
    }
}
