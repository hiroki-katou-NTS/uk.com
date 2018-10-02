package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import nts.arc.primitive.constraint.DecimalMinValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.DecimalPrimitiveValue;

import java.math.BigDecimal;

/**
* 給与単価金額
*/
@DecimalMinValue("-99999999.99")
@DecimalMaxValue("99999999.99")
public class SalaryUnitPrice extends DecimalPrimitiveValue<SalaryUnitPrice>
{
    
    private static final long serialVersionUID = 1L;
    
    public SalaryUnitPrice(BigDecimal rawValue)
    {
         super(rawValue);
    }
    
}
