package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
* 要勤務基準日数
*/
@DecimalMinValue("0.00")
@DecimalMaxValue("99.99")
@DecimalMantissaMaxLength(2)
public class ReqStandardWorkingDays extends DecimalPrimitiveValue<ReqStandardWorkingDays>
{
    
    private static final long serialVersionUID = 1L;
    
    public ReqStandardWorkingDays(BigDecimal rawValue)
    {
         super(rawValue);
    }
    
}
