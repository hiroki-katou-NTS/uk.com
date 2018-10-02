package nts.uk.ctx.pr.core.wageprovision.companyuniformamount;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 会社単価名称
*/
@StringMaxLength(20)
public class SalaryUnitPriceName extends StringPrimitiveValue<SalaryUnitPriceName>
{
    
    private static final long serialVersionUID = 1L;
    
    public SalaryUnitPriceName(String rawValue)
    {
         super(rawValue);
    }
    
}
