package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 任意項目申請種類コード
 */

@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class OptionalItemApplicationTypeCode extends StringPrimitiveValue<OptionalItemApplicationTypeCode> {
    public OptionalItemApplicationTypeCode(String rawValue) {
        super(rawValue);
    }
}
