package nts.uk.ctx.core.dom.printdata;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.StringPrimitiveValue;

/**
* 会計事務所名称
*/
@StringMaxLength(40)
public class AccountingOffName extends StringPrimitiveValue<AccountingOffName>
{
    
    private static final long serialVersionUID = 1L;
    
    public AccountingOffName(String rawValue)
    {
         super(rawValue);
    }
    
}
