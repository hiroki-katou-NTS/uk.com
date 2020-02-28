package nts.uk.ctx.pr.core.domainmodel.nittsusystem.universalk.common;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* メモ
*/
@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<Memo>
{
    
    private static final long serialVersionUID = 1L;
    
    public Memo(String rawValue)
    {
         super(rawValue);
    }
    
}
