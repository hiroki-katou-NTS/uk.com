package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 法人番号
 */
@StringMaxLength(13)
@StringCharType(CharType.NUMERIC)
public class CorporateNumber extends StringPrimitiveValue<CorporateNumber> {
    private static final long serialVersionUID = 1L;

    public CorporateNumber(String rawValue)
    {
        super(rawValue);
    }
}
