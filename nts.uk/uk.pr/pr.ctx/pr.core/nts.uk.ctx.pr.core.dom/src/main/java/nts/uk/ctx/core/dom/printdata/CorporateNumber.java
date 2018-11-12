package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.*;

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
