package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 健康保険番号
 */

/**/
@StringMaxLength(3)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class FdNumber extends StringPrimitiveValue<FdNumber> {
    private static final long serialVersionUID = 1L;

    public FdNumber(String rawValue)
    {
        super(rawValue);
    }
}
