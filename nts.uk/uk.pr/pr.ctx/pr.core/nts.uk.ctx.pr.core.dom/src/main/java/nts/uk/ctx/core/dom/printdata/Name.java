package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 法定調書用会社名
*/
@StringMaxLength(40)
public class Name extends StringPrimitiveValue<Name>
{
    
    private static final long serialVersionUID = 1L;
    
    public Name(String rawValue)
    {
         super(rawValue);
    }
    
}
