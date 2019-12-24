package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.IntegerPrimitiveValue;

/**
* 報酬月額
*/
@IntegerMinValue(0)
@IntegerMaxValue(99999999)
public class RemuneraMonthly extends IntegerPrimitiveValue<RemuneraMonthly>
{
    
    private static final long serialVersionUID = 1L;
    
    public RemuneraMonthly(Integer rawValue)
    {
         super(rawValue);
    }
    
}
