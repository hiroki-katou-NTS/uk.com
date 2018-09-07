package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;

/**
* 要勤務基準日数
*/
public class ReqStandardWorkingDays extends DecimalPrimitiveValue<ReqStandardWorkingDays>
{
    
    private static final long serialVersionUID = 1L;
    
    public ReqStandardWorkingDays(BigDecimal rawValue)
    {
         super(rawValue);
    }
    
}
