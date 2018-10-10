package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
* パラメータ時間
*/
@IntegerMinValue(-9999)
@IntegerMaxValue(9999)
public class ParamTime extends IntegerPrimitiveValue<ParamTime>
{
    
    private static final long serialVersionUID = 1L;
    
    public ParamTime(int rawValue)
    {
         super(rawValue);
    }
    
}
