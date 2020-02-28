package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 金融機関所在地
*/
@StringMaxLength(160)
public class VibLocaFinIns extends StringPrimitiveValue<VibLocaFinIns>
{
    
    private static final long serialVersionUID = 1L;
    
    public VibLocaFinIns(String rawValue)
    {
         super(rawValue);
    }
    
}
