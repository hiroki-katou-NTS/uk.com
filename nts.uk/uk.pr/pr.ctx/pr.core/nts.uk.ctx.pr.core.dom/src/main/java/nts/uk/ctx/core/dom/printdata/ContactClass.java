package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 連絡者所属課係
*/
@StringMaxLength(20)
public class ContactClass extends StringPrimitiveValue<ContactClass>
{
    
    private static final long serialVersionUID = 1L;
    
    public ContactClass(String rawValue)
    {
         super(rawValue);
    }
    
}
