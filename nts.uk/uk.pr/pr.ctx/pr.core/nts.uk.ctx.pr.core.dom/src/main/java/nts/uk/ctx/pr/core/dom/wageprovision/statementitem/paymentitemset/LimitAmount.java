package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

/**
 * 
 * @author thanh.tq 限度金額
 *
 */
@LongMinValue(0L)
@LongMaxValue(9999999999L)
public class LimitAmount extends LongPrimitiveValue<LimitAmount>
{
    
    private static final long serialVersionUID = 1L;
    
    public LimitAmount(long rawValue)
    {
         super(rawValue);
    }
    
}