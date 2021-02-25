package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 任意項目申請の説明文
 */

@StringMaxLength(200)
public class OptionalItemApplicationDescription extends StringPrimitiveValue<OptionalItemApplicationDescription> {
    public OptionalItemApplicationDescription(String rawValue) {
        super(rawValue);
    }
}
