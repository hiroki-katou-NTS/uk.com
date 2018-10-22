package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.*;

/**
 * 法人番号
 */
@IntegerMinValue(1)
@IntegerMaxValue(13)
public class CorporateNumber extends IntegerPrimitiveValue<CorporateNumber> {
    private static final long serialVersionUID = 1L;

    public CorporateNumber(Integer rawValue)
    {
        super(rawValue);
    }
}
