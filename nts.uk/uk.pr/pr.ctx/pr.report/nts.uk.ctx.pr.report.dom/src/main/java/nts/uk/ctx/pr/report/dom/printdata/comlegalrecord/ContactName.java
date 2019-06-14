package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

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
