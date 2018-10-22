package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 事業種目
*/
@StringMaxLength(12)
public class BusinessLine extends StringPrimitiveValue<BusinessLine>
{
    
    private static final long serialVersionUID = 1L;
    
    public BusinessLine(String rawValue)
    {
         super(rawValue);
    }
    
}
