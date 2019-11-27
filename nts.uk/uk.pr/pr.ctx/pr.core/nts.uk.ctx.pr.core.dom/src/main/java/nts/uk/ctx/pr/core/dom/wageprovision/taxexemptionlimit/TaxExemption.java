package nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit;

import nts.arc.primitive.LongPrimitiveValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.constraint.LongMinValue;

/**
 * 
 * @author thanh.tq 非課税限度額
 *
 */
@LongMinValue(0L)
@LongMaxValue(9999999999L)
public class TaxExemption extends LongPrimitiveValue<TaxExemption>
{
    
    private static final long serialVersionUID = 1L;
    
    public TaxExemption(long rawValue)
    {
         super(rawValue);
    }
    
}