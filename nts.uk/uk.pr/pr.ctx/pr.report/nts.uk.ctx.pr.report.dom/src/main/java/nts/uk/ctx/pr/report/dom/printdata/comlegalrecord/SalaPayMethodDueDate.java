package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

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
