package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import nts.arc.primitive.constraint.LongMinValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.LongPrimitiveValue;

/**
* 共通金額
*/
@LongMinValue(-9999999999L)
@LongMaxValue(9999999999L)
public class CommonAmount extends LongPrimitiveValue<CommonAmount>
{
    
    private static final long serialVersionUID = 1L;
    
    public CommonAmount(long rawValue)
    {
         super(rawValue);
    }
    
}
