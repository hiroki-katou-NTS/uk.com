package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

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
