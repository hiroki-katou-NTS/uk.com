package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

import java.math.BigDecimal;

/**
* 固定金額
*/
@DecimalMaxValue("999999999.999")
@DecimalMinValue("0.000")
@DecimalMantissaMaxLength(3)
public class FixedAmount extends DecimalPrimitiveValue<FixedAmount>
{

    private static final long serialVersionUID = 1L;

    public FixedAmount(BigDecimal rawValue)
    {
         super(rawValue);
    }
    
}
