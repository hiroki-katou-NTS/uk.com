package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 経理責任者氏名
*/
@StringMaxLength(20)
public class AccountManaName extends StringPrimitiveValue<AccountManaName>
{
    
    private static final long serialVersionUID = 1L;
    
    public AccountManaName(String rawValue)
    {
         super(rawValue);
    }
    
}
