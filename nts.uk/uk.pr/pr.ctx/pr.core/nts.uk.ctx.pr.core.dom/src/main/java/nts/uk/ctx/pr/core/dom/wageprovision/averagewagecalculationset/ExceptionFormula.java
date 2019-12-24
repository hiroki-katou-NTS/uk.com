package nts.uk.ctx.pr.core.dom.wageprovision.averagewagecalculationset;

import nts.arc.primitive.constraint.IntegerMinValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.IntegerPrimitiveValue;

/**
* 例外計算式割合
*/
@IntegerMinValue(0)
@IntegerMaxValue(100)
public class ExceptionFormula extends IntegerPrimitiveValue<ExceptionFormula>
{
    
    private static final long serialVersionUID = 1L;
    
    public ExceptionFormula(Integer rawValue)
    {
         super(rawValue);
    }
    
}
