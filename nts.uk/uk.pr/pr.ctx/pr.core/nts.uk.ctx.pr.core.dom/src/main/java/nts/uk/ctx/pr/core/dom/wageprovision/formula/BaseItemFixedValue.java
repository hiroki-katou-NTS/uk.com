package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

import java.math.BigDecimal;

/**
* 基底項目固定値
*/
@DecimalMaxValue("999999999")
@DecimalMinValue("0")
@DecimalMantissaMaxLength(0)
public class BaseItemFixedValue extends DecimalPrimitiveValue<BaseItemFixedValue>
{
    
    private static final long serialVersionUID = 1L;
    
    public BaseItemFixedValue(BigDecimal rawValue)
    {
         super(rawValue);
    }
    
}
