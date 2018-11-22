package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
* 賃金テーブル名
*/
@StringMaxLength(30)
public class WageTableName extends StringPrimitiveValue<WageTableName>
{
    
    private static final long serialVersionUID = 1L;
    
    public WageTableName(String rawValue)
    {
         super(rawValue);
    }
    
}
