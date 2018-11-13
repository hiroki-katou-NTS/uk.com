package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

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
