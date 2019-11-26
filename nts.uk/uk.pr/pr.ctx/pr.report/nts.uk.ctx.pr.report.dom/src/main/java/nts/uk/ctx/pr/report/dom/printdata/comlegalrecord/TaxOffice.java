package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 所轄税務署
*/
@StringMaxLength(40)
public class TaxOffice extends StringPrimitiveValue<TaxOffice>
{
    
    private static final long serialVersionUID = 1L;
    
    public TaxOffice(String rawValue)
    {
         super(rawValue);
    }
    
}
