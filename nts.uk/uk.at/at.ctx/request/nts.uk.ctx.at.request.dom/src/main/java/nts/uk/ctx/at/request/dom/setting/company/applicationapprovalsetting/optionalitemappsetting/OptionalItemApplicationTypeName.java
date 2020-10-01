package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 任意項目申請種類名
 */

@StringMaxLength(40)
public class OptionalItemApplicationTypeName extends StringPrimitiveValue<OptionalItemApplicationTypeName> {
    public OptionalItemApplicationTypeName(String rawValue) {
        super(rawValue);
    }
}
