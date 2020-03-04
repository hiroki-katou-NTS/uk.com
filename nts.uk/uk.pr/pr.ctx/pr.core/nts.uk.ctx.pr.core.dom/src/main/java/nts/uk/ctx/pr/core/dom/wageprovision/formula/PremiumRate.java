package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.IntegerPrimitiveValue;

/**
* 割増率
*/
@IntegerMinValue(0)
@IntegerMaxValue(100)
public class PremiumRate extends IntegerPrimitiveValue<PremiumRate>
{
    
    private static final long serialVersionUID = 1L;
    
    public PremiumRate(Integer rawValue)
    {
         super(rawValue);
    }
    
}
