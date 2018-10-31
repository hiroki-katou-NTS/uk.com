package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 明細書名称
*/
@StringMaxLength(20)
public class StatementName extends StringPrimitiveValue<StatementName>
{
    
    private static final long serialVersionUID = 1L;
    
    public StatementName(String rawValue)
    {
         super(rawValue);
    }
    
}
