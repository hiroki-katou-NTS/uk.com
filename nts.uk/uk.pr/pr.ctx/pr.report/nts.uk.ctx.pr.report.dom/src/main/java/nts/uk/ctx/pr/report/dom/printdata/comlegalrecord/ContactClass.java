package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

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
