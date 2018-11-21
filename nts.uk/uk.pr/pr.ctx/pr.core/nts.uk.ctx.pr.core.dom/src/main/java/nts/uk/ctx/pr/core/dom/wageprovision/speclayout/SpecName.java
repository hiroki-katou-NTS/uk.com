package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* SpecName
*/
@StringMaxLength(20)
public class SpecName extends StringPrimitiveValue<SpecName>
{
    
    private static final long serialVersionUID = 1L;
    
    public SpecName(String rawValue)
    {
         super(rawValue);
    }
    
}
