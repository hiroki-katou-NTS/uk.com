package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 連絡者氏名
*/
@StringMaxLength(20)
public class ContactName extends StringPrimitiveValue<ContactName>
{
    
    private static final long serialVersionUID = 1L;
    
    public ContactName(String rawValue)
    {
         super(rawValue);
    }
    
}
