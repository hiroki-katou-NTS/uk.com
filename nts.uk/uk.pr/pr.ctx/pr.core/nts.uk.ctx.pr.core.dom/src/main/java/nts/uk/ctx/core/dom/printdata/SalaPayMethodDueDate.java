package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 給与支払方法と期日
*/
@StringMaxLength(12)
public class SalaPayMethodDueDate extends StringPrimitiveValue<SalaPayMethodDueDate>
{
    
    private static final long serialVersionUID = 1L;
    
    public SalaPayMethodDueDate(String rawValue)
    {
         super(rawValue);
    }
    
}
