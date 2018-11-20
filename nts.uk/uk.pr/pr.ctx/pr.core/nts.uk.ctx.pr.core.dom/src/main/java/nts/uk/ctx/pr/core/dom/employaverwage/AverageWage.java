package nts.uk.ctx.pr.core.dom.employaverwage;

import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.primitive.constraint.LongMinValue;
import nts.arc.primitive.constraint.LongMaxValue;
import nts.arc.primitive.LongPrimitiveValue;

/**
* AverageWage
*/
@LongMinValue(0L)
@LongMaxValue(9999999999L)
public class AverageWage extends LongPrimitiveValue<AverageWage>
{
    
    private static final long serialVersionUID = 1L;
    
    public AverageWage(long rawValue)
    {
         super(rawValue);
    }
    
}
