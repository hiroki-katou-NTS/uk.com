package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

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
