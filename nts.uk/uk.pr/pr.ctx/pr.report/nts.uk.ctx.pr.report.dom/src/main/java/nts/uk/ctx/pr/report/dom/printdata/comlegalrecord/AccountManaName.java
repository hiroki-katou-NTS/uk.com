package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

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
